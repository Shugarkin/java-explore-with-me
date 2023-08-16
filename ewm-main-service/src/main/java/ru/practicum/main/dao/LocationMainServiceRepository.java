package ru.practicum.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.model.Location;

import java.util.Optional;

public interface LocationMainServiceRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByLatAndLon(String lat, String lon);
}
