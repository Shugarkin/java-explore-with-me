package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.AdminUpdateEventStatus;
import ru.practicum.dto.State;
import ru.practicum.main.dao.CategoriesMainServiceRepository;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.AdminEvent;
import ru.practicum.main.model.Event;
import ru.practicum.main.model.EventFull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final EventMainServiceRepository repository;

    private final CategoriesMainServiceRepository categoriesMainServiceRepository;

    private final StatService statService;

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

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(List.of(event));
        Map<Long, Long> view = statService.toView(List.of(event));

        EventFull eventFull = EventMapper.toEventFull(event, view.get(eventId), confirmedRequest.get(eventId));

        return eventFull;
    }

    @Override
    public List<EventFull> getAdminEvents(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("id").ascending());

        List<Event> list = repository.findAllByParam(users, states, categories, rangeStart, rangeEnd, pageable);

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(list);
        Map<Long, Long> view = statService.toView(list);

        List<EventFull> listEventFull = new ArrayList<>();

        list.forEach(
                event -> listEventFull.add(
                        EventMapper.toEventFull(event, view.getOrDefault(event.getId(), 0L), confirmedRequest.getOrDefault(event.getId(), 0L))));

        return listEventFull;
    }
}
