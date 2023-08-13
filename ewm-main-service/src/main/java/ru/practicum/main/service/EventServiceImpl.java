package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatClient;
import ru.practicum.dto.AdminUpdateEventStatus;
import ru.practicum.dto.State;
import ru.practicum.dto.UpdateEventStatus;
import ru.practicum.main.dao.*;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private final EventMainServiceRepository repository;

    private final CategoriesMainServiceRepository categoriesMainServiceRepository;

    private final UserMainServiceRepository userMainServiceRepository;

    private final RequestMainServiceRepository requestMainServiceRepository;

    private final LocationMainServiceRepository locationMainServiceRepository;

    private final StatClient statClient;

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

        Map<Long, Long> confirmedRequest = toConfirmedRequest(listEvent);

        Map<Long, Long> mapView = toView(listEvent);

        List<EventFull> listEventFull = new ArrayList<>();

        listEvent.forEach(
                event -> listEventFull.add(
                        EventMapper.toEventFull(event, mapView.getOrDefault(event.getId(), 0L), confirmedRequest.getOrDefault(event.getId(), 0L))));

        return listEventFull;
    }

    @Override
    public EventFull getEventByUserIdAndEventId(long userId, long eventId) {
        Event event = repository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> new NotFoundException("Ивент не найден"));

        Map<Long, Long> confirmedRequest = toConfirmedRequest(List.of(event));

        Map<Long, Long> mapView = toView(List.of(event));

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

        Map<Long, Long> view = toView(List.of(event));
        Map<Long, Long> confirmedRequest = toConfirmedRequest(List.of(event));


        return EventMapper.toEventFull(event, view.get(eventId), confirmedRequest.get(eventId));
    }

    @Transactional
    @Override
    public EventFull patchAdminEvent(long eventId, AdminEvent eventNew) {
        Event event = repository.findById(eventId).orElseThrow(() -> new NotFoundException("События " + eventId + " не найденно"));

        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ConflictException("Время изменить данное событие уже упущенно");
        }
        if (eventNew.getEventDate() != null) {
            if (eventNew.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
                event.setEventDate(eventNew.getEventDate());
            } else {
                throw new ConflictException("Поздновато для изменений даты начала события");
            }
        }
        if (eventNew.getStateAction() != null) {
            if (!event.getState().equals(State.PENDING)) {
                throw new ConflictException("Нельзя изменять событие не находящееся в статусе ожидания");
            }

            if (eventNew.getStateAction().equals(AdminUpdateEventStatus.PUBLISH_EVENT)) {
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now().withNano(0));
            }
            if (eventNew.getStateAction().equals(AdminUpdateEventStatus.REJECT_EVENT)) {
                event.setState(State.CANCELED);
            }
        }

        if (eventNew.getRequestModeration() != null) {
            event.setRequestModeration(eventNew.getRequestModeration());
        }
        if (eventNew.getPaid() != null) {
            event.setPaid(eventNew.getPaid());
        }
        if (eventNew.getParticipantLimit() != null) {
            event.setParticipantLimit(eventNew.getParticipantLimit());
        }
        if (eventNew.getLocation() != null) {
            event.setLocation(eventNew.getLocation());
        }
        if (eventNew.getAnnotation() != null) {
            event.setAnnotation(event.getAnnotation());
        }
        if (eventNew.getDescription() != null) {
            event.setDescription(eventNew.getDescription());
        }
        if (eventNew.getTitle() != null) {
            event.setTitle(event.getTitle());
        }
        if (eventNew.getCategory() != null) {
            event.setCategory(categoriesMainServiceRepository.findById(eventNew.getCategory())
                    .orElseThrow(() -> new NotFoundException("Категория не найдена")));
        }

        Map<Long, Long> confirmedRequest = toConfirmedRequest(List.of(event));
        Map<Long, Long> view = toView(List.of(event));

        EventFull eventFull = EventMapper.toEventFull(event, view.get(eventId), confirmedRequest.get(eventId));

        return eventFull;
    }

    private Map<Long, Long> toConfirmedRequest(List<Event> list) {
        List<Long> listEventId = list.stream().map(a -> a.getId()).collect(Collectors.toList());
        List<ConfirmedRequestShort> confirmedRequestShorts = requestMainServiceRepository.countByEventId(listEventId);
        Map<Long, Long> mapConRequest = confirmedRequestShorts.stream()
                .collect(Collectors.toMap(ConfirmedRequestShort::getEventId, a -> a.getConfirmedRequestsCount()));

        return mapConRequest; //получил количество запросов на ивент
    }

    private Map<Long, Long> toView(List<Event> list) {
        Map<Long, Long> mapView = new HashMap<>();
        LocalDateTime start = list.stream().map(a -> a.getCreatedOn()).min(LocalDateTime::compareTo).orElse(LocalDateTime.now());
        List<String> uris = list.stream().map(a -> "event/" + a.getId()).collect(Collectors.toList());

        ResponseEntity<Object> statEvent = statClient.getStatEvent(start.format(FORMATTER), LocalDateTime.now().withNano(0).format(FORMATTER), uris, true);
        List<Stat> stat = (List<Stat>) statEvent.getBody();
        stat.forEach(statUniqueOrNot -> mapView.put(
                Long.parseLong(statUniqueOrNot.getUri().replaceAll("[\\D]+", "")),
                statUniqueOrNot.getHits()
                )
        );

        return mapView; //получил количество просмотров на ивент
    }
}
