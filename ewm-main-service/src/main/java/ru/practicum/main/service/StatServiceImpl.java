package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatClient;
import ru.practicum.dto.StatDto;
import ru.practicum.main.dao.RequestMainServiceRepository;
import ru.practicum.main.model.ConfirmedRequestShort;
import ru.practicum.main.model.Event;
import ru.practicum.main.model.Stat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatServiceImpl implements StatService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final RequestMainServiceRepository requestMainServiceRepository;

    private final StatClient statClient;

    @Override
    public Map<Long, Long> toConfirmedRequest(List<Event> list) {
        List<Long> listEventId = list.stream().map(a -> a.getId()).collect(Collectors.toList());
        List<ConfirmedRequestShort> confirmedRequestShorts = requestMainServiceRepository.countByEventId(listEventId);
        Map<Long, Long> mapConRequest = confirmedRequestShorts.stream()
                .collect(Collectors.toMap(ConfirmedRequestShort::getEventId, ConfirmedRequestShort::getConfirmedRequestsCount));

        return mapConRequest; //получил количество запросов на ивент
    }

    @Override
    public Map<Long, Long> toView(List<Event> list) {
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

    @Transactional
    @Override
    public void addHits(StatDto statDto) {
        statClient.postStatEvent(statDto);
    }
}
