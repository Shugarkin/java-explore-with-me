package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.*;
import ru.practicum.main.dao.*;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.*;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
        return repository.save(event);
    }

    @Override
    public List<EventFull> getEventByUserId(long userId, int from, int size) {
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

        List<EventFull> listEventFull = new ArrayList<>();

        listEvent.forEach(
                event -> listEventFull.add(
                        EventMapper.toEventFull(event, mapView.getOrDefault(event.getId(), 0L), confirmedRequest.getOrDefault(event.getId(), 0L))));

        return listEventFull;
    }

    @Override
    public EventFull getEventByUserIdAndEventId(long userId, long eventId) {
        Event event = repository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> new NotFoundException("Ивент не найден"));

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(List.of(event));

        Map<Long, Long> mapView = statService.toView(List.of(event));

        EventFull eventFull = EventMapper.toEventFull(event, mapView.get(eventId), confirmedRequest.get(eventId));
        return eventFull;
    }

    @Transactional
    @Override
    public EventFull patchEvent(long userId, long eventId, UpdateEvent updateEvent) {
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
                throw new ConflictException("Дата и время события не могут быть раньше чем за 2 часа до данного момента");
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
            event.setLocation(locationMainServiceRepository.findByLatAndLon(updateEvent.getLocation().getLat(), updateEvent.getLocation().getLon()));
        }

        Map<Long, Long> view = statService.toView(List.of(event));
        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(List.of(event));


        return EventMapper.toEventFull(event, view.get(eventId), confirmedRequest.get(eventId));
    }



    @Override
    public List<Request> getRequestByUserIdAndEventId(long userId, long eventId) {
        boolean answer = repository.existsByIdAndInitiatorId(eventId, userId);
        if (!answer) {
            throw new ConflictException("Вы не являетесь инициатором события");
        }

        List<Request> list = requestMainServiceRepository.findAllByEventId(eventId);
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

        if (event.getParticipantLimit() != 0 && confirmedRequest > event.getParticipantLimit()) {
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

        return updateRequest;
    }

}
