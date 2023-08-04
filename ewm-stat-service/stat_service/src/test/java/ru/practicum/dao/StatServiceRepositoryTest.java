package ru.practicum.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.model.Stat;

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
            .hits(1)
            .hitsUnique(1)
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
    void existsByUri() {
        boolean b = serviceRepository.existsByUri("@DataJpaTest");
        assertEquals(b, false);
    }

    @Test
    void findByUri() {
        Stat byUri = serviceRepository.findByUri("/events");
        assertEquals(byUri, stat);
    }

    @Test
    void existsByUriAndIp() {
        boolean b = serviceRepository.existsByUriAndIp("@DataJpaTest", "@DataJpaTest");
        assertEquals(b, false);
    }

    @Test
    void findByTimestampBetweenAndUri() {
        List<Stat> list =
                serviceRepository.findByTimestampBetweenAndUri(LocalDateTime.parse("2025-05-05 00:00:00", formatter),
                        LocalDateTime.parse("2045-05-05 00:00:00", formatter),
                        List.of("/events"));

        assertEquals(list, List.of(stat));
    }

    @Test
    void findByTimestampBetween() {
        List<Stat> list =
                serviceRepository.findByTimestampBetween(LocalDateTime.parse("2025-05-05 00:00:00", formatter),
                        LocalDateTime.parse("2045-05-05 00:00:00", formatter));

        assertEquals(list, List.of(stat));
    }
}