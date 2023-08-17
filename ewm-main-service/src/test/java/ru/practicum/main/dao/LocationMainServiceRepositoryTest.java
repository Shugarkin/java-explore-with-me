package ru.practicum.main.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.main.model.Location;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class LocationMainServiceRepositoryTest {

    @Autowired
    private LocationMainServiceRepository repository;

    @AfterEach
    void after() {
        repository.deleteAll();
    }

    @Test
    void findByLatAndLon() {
        repository.save(Location.builder().lon("1").lat("2").build());

        Optional<Location> byLatAndLon = repository.findByLatAndLon("2", "1");

        assertNotNull(byLatAndLon.get());
    }
}