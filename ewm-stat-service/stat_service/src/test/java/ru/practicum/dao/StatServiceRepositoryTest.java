package ru.practicum.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StatServiceRepositoryTest {


    @Autowired
    private StatServiceRepository serviceRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Stat stat = Stat.builder()
            .ip("85.249.18.233")
            .uri("/events")
            .timestamp(LocalDateTime.parse("2035-05-05 00:00:00", formatter))
            .app("ewm-main-service")
            .build();

    @BeforeEach
    void before() {
        serviceRepository.save(stat);
    }

    @AfterEach
    void after() {
        serviceRepository.delete(stat);
    }

    @Test
    void findAllByTimestampBetween() {
        List<StatUniqueOrNot> list = serviceRepository.findAllByTimestampBetween(LocalDateTime.parse("2025-05-05 00:00:00", formatter),
                LocalDateTime.parse("2035-05-05 00:00:00", formatter));
        assertEquals(1, list.size());
    }

    @Test
    void findAllByUriAndIp() {
        List<StatUniqueOrNot> list = serviceRepository.findAllByUriAndIp(LocalDateTime.parse("2025-05-05 00:00:00", formatter),
                LocalDateTime.parse("2035-05-05 00:00:00", formatter));
        assertEquals(1, list.size());
    }

    @Test
    void findAllByUriAndIpAndUris() {
        List<StatUniqueOrNot> list = serviceRepository.findAllByUriAndIpAndUris(LocalDateTime.parse("2025-05-05 00:00:00", formatter),
                LocalDateTime.parse("2035-05-05 00:00:00", formatter), List.of("/events"));
        assertEquals(1, list.size());
    }

    @Test
    void findAllByUriAndIpAndUrisNotUnique() {
        List<StatUniqueOrNot> list = serviceRepository.findAllByUriAndIpAndUrisNotUnique(LocalDateTime.parse("2025-05-05 00:00:00", formatter),
                LocalDateTime.parse("2035-05-05 00:00:00", formatter), List.of("/events"));
        assertEquals(1, list.size());
    }
}