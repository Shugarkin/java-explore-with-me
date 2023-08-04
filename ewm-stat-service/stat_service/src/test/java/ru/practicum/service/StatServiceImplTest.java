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
    void postStatUnrecorded() {

        when(serviceRepository.existsByUri(stat.getUri())).thenReturn(false); //записей пока нет
        when(serviceRepository.save(stat)).thenReturn(stat);

        Stat stat1 = statService.postStat(stat);

        assertNotNull(stat1);

    }

    @Test
    void postStatRecordedNotIp() {
        when(serviceRepository.existsByUri(stat.getUri())).thenReturn(true); //запись уже есть
        when(serviceRepository.existsByUriAndIp(stat.getUri(), stat.getIp())).thenReturn(false);//обращений с этого айпи нет
        when(serviceRepository.findByUri(stat.getUri())).thenReturn(stat);

        Stat stat1 = statService.postStat(stat);

        assertNotNull(stat1);
    }

    @Test
    void postStatRecordedWithIp() {
        when(serviceRepository.existsByUri(stat.getUri())).thenReturn(true); //запись уже есть
        when(serviceRepository.existsByUriAndIp(stat.getUri(), stat.getIp())).thenReturn(true);//обращения с этого айпи есть
        when(serviceRepository.findByUri(stat.getUri())).thenReturn(stat);

        Stat stat1 = statService.postStat(stat);

        assertNotNull(stat1);
    }


    @Test
    void getUniqueStatWithoutUris() {
        String start = "2020-05-05 00:00:00";
        String end = "2035-05-05 00:00:00";
        List<String> uris = List.of();
        boolean unique = false;

        LocalDateTime dateStart = LocalDateTime.parse(start, formatter);
        LocalDateTime dateEnd = LocalDateTime.parse(end, formatter);

        when(serviceRepository.findByTimestampBetween(dateStart, dateEnd)).thenReturn(List.of(stat));

        List<StatUniqueOrNot> list = statService.getStat(start, end, uris, unique);

        assertNotEquals(list.size(), 0);
    }

    @Test
    void getNotUniqueStatWithoutUris() {
        String start = "2020-05-05 00:00:00";
        String end = "2035-05-05 00:00:00";
        List<String> uris = List.of();
        boolean unique = true;

        LocalDateTime dateStart = LocalDateTime.parse(start, formatter);
        LocalDateTime dateEnd = LocalDateTime.parse(end, formatter);

        when(serviceRepository.findByTimestampBetween(dateStart, dateEnd)).thenReturn(List.of(stat));

        List<StatUniqueOrNot> list = statService.getStat(start, end, uris, unique);

        assertNotEquals(list.size(), 0);
    }

    @Test
    void getUniqueStatWithUris() {
        String start = "2020-05-05 00:00:00";
        String end = "2035-05-05 00:00:00";
        List<String> uris = List.of("/events");
        boolean unique = true;

        LocalDateTime dateStart = LocalDateTime.parse(start, formatter);
        LocalDateTime dateEnd = LocalDateTime.parse(end, formatter);

        when(serviceRepository.findByTimestampBetweenAndUri(dateStart, dateEnd, uris)).thenReturn(List.of(stat));

        List<StatUniqueOrNot> list = statService.getStat(start, end, uris, unique);

        assertNotEquals(list.size(), 0);
    }

    @Test
    void getNotUniqueStatWithUris() {
        String start = "2020-05-05 00:00:00";
        String end = "2035-05-05 00:00:00";
        List<String> uris = List.of("/events");
        boolean unique = false;

        LocalDateTime dateStart = LocalDateTime.parse(start, formatter);
        LocalDateTime dateEnd = LocalDateTime.parse(end, formatter);

        when(serviceRepository.findByTimestampBetweenAndUri(dateStart, dateEnd, uris)).thenReturn(List.of(stat));

        List<StatUniqueOrNot> list = statService.getStat(start, end, uris, unique);

        assertNotEquals(list.size(), 0);
    }
}