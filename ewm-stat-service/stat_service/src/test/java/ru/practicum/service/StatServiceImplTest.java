package ru.practicum.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dao.StatServiceRepository;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatServiceImplTest {

    @Mock
    private StatServiceRepository serviceRepository;

    @InjectMocks
    private StatServiceImpl statService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Stat stat = Stat.builder()
            .ip("85.249.18.233")
            .uri("/events")
            .timestamp(LocalDateTime.parse("2035-05-05 00:00:00", formatter))
            .app("ewm-main-service")
            .build();

    @Test
    void postStat() {

        when(serviceRepository.save(stat)).thenReturn(stat);

        Stat stat1 = statService.postStat(stat);

        assertNotNull(stat1);

    }


    @Test
    void getUniqueStatWithoutUris() {
        LocalDateTime start = LocalDateTime.parse("2020-05-05 00:00:00", formatter);
        LocalDateTime end = LocalDateTime.parse("2035-05-05 00:00:00", formatter);
        List<String> uris = List.of();
        boolean unique = true;

        List<StatUniqueOrNot> list = List.of(StatUniqueOrNot.builder().hits(1).uri("/events").app("ewm-main-service").build());

        when(serviceRepository.findAllByUriAndIp(start, end)).thenReturn(list);

        List<StatUniqueOrNot> newList = statService.getStat(start, end, uris, unique);

        assertNotEquals(newList.size(), 0);
    }

    @Test
    void getNotUniqueStatWithoutUris() {
        LocalDateTime start = LocalDateTime.parse("2020-05-05 00:00:00", formatter);
        LocalDateTime end = LocalDateTime.parse("2035-05-05 00:00:00", formatter);
        List<String> uris = List.of();
        boolean unique = false;

        List<StatUniqueOrNot> list = List.of(StatUniqueOrNot.builder().hits(1).uri("/events").app("ewm-main-service").build());

        when(serviceRepository.findAllByTimestampBetween(start, end)).thenReturn(list);

        List<StatUniqueOrNot> newList = statService.getStat(start, end, uris, unique);

        assertNotEquals(newList.size(), 0);
    }

    @Test
    void getUniqueStatWithUris() {
        LocalDateTime start = LocalDateTime.parse("2020-05-05 00:00:00", formatter);
        LocalDateTime end = LocalDateTime.parse("2035-05-05 00:00:00", formatter);
        List<String> uris = List.of("/events");
        boolean unique = true;

        List<StatUniqueOrNot> list = List.of(StatUniqueOrNot.builder().hits(1).uri("/events").app("ewm-main-service").build());

        when(serviceRepository.findAllByUriAndIpAndUris(start, end, uris)).thenReturn(list);

        List<StatUniqueOrNot> newList = statService.getStat(start, end, uris, unique);

        assertNotEquals(newList.size(), 0);
    }

    @Test
    void getNotUniqueStatWithUris() {
        LocalDateTime start = LocalDateTime.parse("2020-05-05 00:00:00", formatter);
        LocalDateTime end = LocalDateTime.parse("2035-05-05 00:00:00", formatter);
        List<String> uris = List.of("/events");
        boolean unique = false;

        List<StatUniqueOrNot> list = List.of(StatUniqueOrNot.builder().hits(1).uri("/events").app("ewm-main-service").build());

        when(serviceRepository.findAllByUriAndIpAndUrisNotUnique(start, end, uris)).thenReturn(list);

        List<StatUniqueOrNot> newList = statService.getStat(start, end, uris, unique);

        assertNotEquals(newList.size(), 0);
    }

}