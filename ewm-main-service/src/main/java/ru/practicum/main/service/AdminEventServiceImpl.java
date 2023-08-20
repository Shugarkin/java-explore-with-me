package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.dto.AdminUpdateEventStatus;
import ru.practicum.main.dto.State;
import ru.practicum.main.dao.CategoriesMainServiceRepository;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.dao.LocationMainServiceRepository;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.model.AdminEvent;
import ru.practicum.main.model.Event;
import ru.practicum.main.model.Location;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {

    private final EventMainServiceRepository repository;

    private final CategoriesMainServiceRepository categoriesMainServiceRepository;

    private final LocationMainServiceRepository locationMainServiceRepository;

    private final StatService statService;

    @Transactional
    @Override
    public Event patchAdminEvent(long eventId, AdminEvent eventNew) {
        Event event = repository.findById(eventId).orElseThrow(() -> new NotFoundException("События " + eventId + " не найденно"));

        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new BadRequestException("Время изменить данное событие уже упущенно");
        }
        if (eventNew.getEventDate() != null) {
            if (eventNew.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
                throw new BadRequestException("Поздновато для изменений даты начала события");
            } else {
                event.setEventDate(eventNew.getEventDate());
            }
        }
        if (eventNew.getStateAction() != null) {
            if (!event.getState().equals(State.PENDING)) {
                throw new ConflictException("Нельзя изменять событие не находящееся в статусе ожидания, опубликованное или отмененное");
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
            event.setLocation(getLocation(eventNew.getLocation()).orElse(saveLocation(eventNew.getLocation())));
        }
        if (eventNew.getAnnotation() != null && !eventNew.getTitle().isBlank()) {
            event.setAnnotation(eventNew.getAnnotation());
        }
        if (eventNew.getDescription() != null && !eventNew.getDescription().isBlank()) {
            event.setDescription(eventNew.getDescription());
        }
        if (eventNew.getTitle() != null && !eventNew.getTitle().isBlank()) {
            event.setTitle(eventNew.getTitle());
        }
        if (eventNew.getCategory() != null) {
            event.setCategory(categoriesMainServiceRepository.findById(eventNew.getCategory())
                    .orElseThrow(() -> new NotFoundException("Категория не найдена")));
        }

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(List.of(event));
        Map<Long, Long> view = statService.toView(List.of(event));

        event.setView(view.getOrDefault(eventId, 0L));
        event.setConfirmedRequests(confirmedRequest.getOrDefault(eventId, 0L));
        log.info("path event for admin");
        return event;
    }

    @Override
    public List<Event> getAdminEvents(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("id").ascending());

        List<Event> list = repository.findAllByParam(users, states, categories, rangeStart, rangeEnd, pageable);

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(list);
        Map<Long, Long> view = statService.toView(list);

        list.forEach(event -> {
            event.setConfirmedRequests(confirmedRequest.getOrDefault(event.getId(), 0L));
            event.setView(view.getOrDefault(event.getId(), 0L));
        });
        log.info("get event for admin");
        return list;
    }

    private Optional<Location> getLocation(Location location) {
        log.info("find location");
        return locationMainServiceRepository.findByLatAndLon(location.getLat(), location.getLon());
    }

    private Location saveLocation(Location location) {
        log.info("save new location");
        return locationMainServiceRepository.save(location);
    }
}
