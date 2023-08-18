package ru.practicum.main.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatClient;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatUniqueOrNotDto;
import ru.practicum.main.dao.RequestMainServiceRepository;
import ru.practicum.main.exception.StatException;
import ru.practicum.main.mapper.StatMapper;
import ru.practicum.main.model.ConfirmedRequestShort;
import ru.practicum.main.model.Event;
import ru.practicum.dto.Stat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatServiceImpl implements StatService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final RequestMainServiceRepository requestMainServiceRepository;

    private final StatClient statClient;

    private final ObjectMapper objectMapper;

    @Override
    public Map<Long, Long> toConfirmedRequest(Collection<Event> list) {
        List<Long> listEventId = list.stream().map(a -> a.getId()).collect(Collectors.toList());
        List<ConfirmedRequestShort> confirmedRequestShorts = requestMainServiceRepository.countByEventId(listEventId);
        Map<Long, Long> mapConRequest = confirmedRequestShorts.stream()
                .collect(Collectors.toMap(ConfirmedRequestShort::getEventId, ConfirmedRequestShort::getConfirmedRequestsCount));
        log.info("get confirmed request");
        return mapConRequest; //получил количество запросов на ивент
    }

    @Override
    public Map<Long, Long> toView(Collection<Event> list) {
        Map<Long, Long> mapView = new HashMap<>();
        LocalDateTime start = list.stream().map(a -> a.getCreatedOn()).min(LocalDateTime::compareTo).orElse(LocalDateTime.now());
        List<String> uris = list.stream().map(a -> "/events/" + a.getId()).collect(Collectors.toList());

        ResponseEntity<Object> response = statClient.getStatEvent(start.format(FORMATTER), LocalDateTime.now().format(FORMATTER), uris, true);

        try {
            List<StatUniqueOrNotDto> stat = Arrays.asList(objectMapper.readValue(
                    objectMapper.writeValueAsString(response.getBody()), StatUniqueOrNotDto[].class));

            List<Stat> listStat = StatMapper.toListStat(stat);

            listStat.forEach(statistic -> mapView.put(
                            Long.parseLong(statistic.getUri().replaceAll("[\\D]+", "")),
                    statistic.getHits()
                    )
            );

        } catch (JsonProcessingException e) {
            throw new StatException("Произошла ошибка выполнения запроса статистики");
        }
        log.info("get view");
        return mapView; //получил количество просмотров на ивент
    }

    @Transactional
    @Override
    public void addHits(StatDto statDto) {
        statClient.postStatEvent(statDto);
        log.info("add hits");
    }
}
