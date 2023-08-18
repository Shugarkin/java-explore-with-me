package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.*;
import ru.practicum.main.dao.*;
import ru.practicum.main.dto.State;
import ru.practicum.main.dto.UpdateEventStatus;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.model.*;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PrivateEventServiceImpl implements PrivateEventService {


    private final EventMainServiceRepository repository;

    private final CategoriesMainServiceRepository categoriesMainServiceRepository;

    private final UserMainServiceRepository userMainServiceRepository;

    private final RequestMainServiceRepository requestMainServiceRepository;

    private final LocationMainServiceRepository locationMainServiceRepository;

    private final StatService statService;

    @Transactional
    @Override
    public Event createEvent(long userId, Event event) {
        event.setCategory(categoriesMainServiceRepository.findById(event.getCategory().getId()).orElseThrow(() -> new NotFoundException("Категория не найдена")));
        event.setCreatedOn(LocalDateTime.now().withNano(0));
        event.setLocation(locationMainServiceRepository.save(event.getLocation()));
        event.setInitiator(userMainServiceRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден")));
        event.setState(State.PENDING);
        log.info("create new event");
        return repository.save(event);
    }

    @Override
    public List<Event> getEventByUserId(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("id").ascending());
        boolean answer = userMainServiceRepository.existsById(userId);
        if (!answer) {
            throw new NotFoundException("Пользователь не найден");
        }
        List<Event> listEvent = repository.findAllByInitiatorId(userId, pageable); //получил ивенты созданные этим пользователем
        if (listEvent.isEmpty()) {
            return List.of();
        }

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(listEvent);

        Map<Long, Long> mapView = statService.toView(listEvent);

        listEvent.forEach(event -> {
            event.setConfirmedRequests(confirmedRequest.getOrDefault(event.getId(), 0L));
            event.setView(mapView.getOrDefault(event.getId(), 0L));
        });
        log.info("get event by userID");
        return listEvent;
    }

    @Override
    public Event getEventByUserIdAndEventId(long userId, long eventId) {
        Event event = repository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> new NotFoundException("Ивент не найден"));

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(List.of(event));

        Map<Long, Long> mapView = statService.toView(List.of(event));

        event.setView(mapView.getOrDefault(eventId, 0L));
        event.setConfirmedRequests(confirmedRequest.getOrDefault(eventId, 0L));
        log.info("get event by userID and eventID");
        return event;
    }

    @Transactional
    @Override
    public Event patchEvent(long userId, long eventId, UpdateEvent updateEvent) {
        Event event = repository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> new NotFoundException("Ивент не найден"));

        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("ВЫ не создавали этот евент");
        }

        if (event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Нельзя изменить уже опубликованные события");
        }

        LocalDateTime eventTime = updateEvent.getEventDate();
        if (eventTime != null) {
            if (eventTime.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new BadRequestException("Дата и время события не могут быть раньше чем за 2 часа до данного момента");
            }
            event.setEventDate(eventTime);
        }

        UpdateEventStatus status = updateEvent.getStateAction();
        if (status != null) {
            if (status.equals(UpdateEventStatus.SEND_TO_REVIEW)) {
                event.setState(State.PENDING);
            }
            if (status.equals(UpdateEventStatus.CANCEL_REVIEW)) {
                event.setState(State.CANCELED);
            }
        }
        if (updateEvent.getRequestModeration() != null) {
            event.setRequestModeration(updateEvent.getRequestModeration());
        }
        if (updateEvent.getPaid() != null) {
            event.setPaid(updateEvent.getPaid());
        }
        if (updateEvent.getDescription() != null) {
            event.setDescription(updateEvent.getDescription());
        }
        if (updateEvent.getAnnotation() != null) {
            event.setAnnotation(updateEvent.getAnnotation());
        }
        if (updateEvent.getTitle() != null) {
            event.setTitle(updateEvent.getTitle());
        }

        if (updateEvent.getCategory() != null) {
            event.setCategory(categoriesMainServiceRepository.findById(updateEvent.getCategory())
                    .orElseThrow(() -> new NotFoundException("Категория не найдена")));
        }
        if (updateEvent.getLocation() != null) {
            event.setLocation(getLocation(updateEvent.getLocation()).orElse(saveLocation(updateEvent.getLocation())));
        }

        Map<Long, Long> view = statService.toView(List.of(event));
        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(List.of(event));

        event.setView(view.getOrDefault(eventId, 0L));
        event.setConfirmedRequests(confirmedRequest.getOrDefault(eventId, 0L));
        log.info("patch event");
        return event;
    }



    @Override
    public List<Request> getRequestByUserIdAndEventId(long userId, long eventId) {
        boolean answer = repository.existsByIdAndInitiatorId(eventId, userId);
        if (!answer) {
            throw new ConflictException("Вы не являетесь инициатором события");
        }

        List<Request> list = requestMainServiceRepository.findAllByEventId(eventId);
        log.info("get list request");
        return list;
    }

    @Transactional
    @Override
    public RequestShortUpdate patchRequestByOwnerUser(long userId, long eventId, RequestShort requestShort) {
        boolean answerUser = userMainServiceRepository.existsById(userId);
        if (!answerUser) {
            throw new NotFoundException("Пользователя с id " + userId + " не существует");
        }
        Event event = repository.findById(eventId).orElseThrow(() -> new NotFoundException("События с id " + eventId + " не существует"));

        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Вы не являетесь инициатором события");
        }

        int confirmedRequest = statService.toConfirmedRequest(List.of(event)).values().size();

        if (event.getParticipantLimit() != 0 && confirmedRequest >= event.getParticipantLimit()) {
            throw new ConflictException("Нет свободный заявок на участие");
        }

        RequestShortUpdate updateRequest = new RequestShortUpdate();

        requestShort.getRequestIds().forEach(requestId -> {

            Request request = requestMainServiceRepository.findById(requestId).orElseThrow(() -> new NotFoundException("Данного запроса не найденно"));

            if (requestShort.getStatus().equals(Status.CONFIRMED)) {
                request.setStatus(Status.CONFIRMED);
                updateRequest.getConformedRequest().add(request);
            }
            if ((requestShort.getStatus().equals(Status.REJECTED))) {
                request.setStatus(Status.REJECTED);
                updateRequest.getCanselRequest().add(request);
            }
        });
        log.info("update request");
        return updateRequest;
    }

    private Optional<Location> getLocation(Location location) {
        log.info("get location");
        return locationMainServiceRepository.findByLatAndLon(location.getLat(), location.getLon());
    }

    private Location saveLocation(Location location) {
        log.info("save new location user");
        return locationMainServiceRepository.save(location);
    }
}
