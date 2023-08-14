package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.StatDto;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.Event;
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
public class PublicEventServiceImpl implements PublicEventService {

    private final EventMainServiceRepository repository;

    private final StatService statService;


    @Override
    public List<EventShort> getPublicEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request) {

        if (sort.equals("EVENT_DATE")) {
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
        statService.addHits(StatDto.builder()
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().withNano(0))
                .app("ewm-main-service")
                .build());
        return listShort;
    }
}
