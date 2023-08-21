package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.dao.CommentMainServiceRepository;
import ru.practicum.main.dto.State;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.Comment;
import ru.practicum.main.model.Event;
import ru.practicum.main.model.EventFullWithComment;
import ru.practicum.main.model.EventShort;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PublicEventServiceImpl implements PublicEventService {

    private final EventMainServiceRepository repository;

    private final CommentMainServiceRepository commentMainServiceRepository;

    private final StatService statService;


    @Override
    public List<EventShort> getPublicEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request) {

        if (sort != null && sort.equals("EVENT_DATE")) {
            sort = "eventDate";
        } else {
            sort = "id";
        }

        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by(sort).descending());

        List<Event> list = repository.findAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, pageable);

        Map<Long, Long> view = statService.toView(list);
        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(list);

        List<EventShort> listShort = new ArrayList<>();

        list.forEach(event -> listShort.add(EventMapper.toEventShort(event, view.getOrDefault(event.getId(), 0L),
                confirmedRequest.getOrDefault(event.getId(), 0L))));

        if (sort.equals("VIEWS")) {
            listShort.sort(Comparator.comparingLong(EventShort::getViews));
        }
        statService.addHits(request);
        log.info("get public events");
        return listShort;
    }

    @Override
    public EventFullWithComment getPublicEvent(long id, HttpServletRequest request) {
        Event event = repository.findById(id).orElseThrow(() -> new NotFoundException("Событие с id " + id + " не найдено"));

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new NotFoundException("Событие с id " + id + " не опубликованно");
        }

        Map<Long, Long> view = statService.toView(List.of(event));
        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(List.of(event));

        Pageable pageable = PageRequest.of(0, 5, Sort.by("createTime").ascending());
        List<Comment> commentList = commentMainServiceRepository.findAllByEventId(id, pageable);
        statService.addHits(request);

        event.setView(view.getOrDefault(event.getId(), 0L));
        event.setConfirmedRequests(confirmedRequest.getOrDefault(event.getId(), 0L));

        log.info("get public event");
        return EventMapper.toEventWithComment(event, commentList);
    }
}
