package ru.practicum.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.model.Location;

public interface LocationMainServiceRepository extends JpaRepository<Location, Long> {
    Location findByLatAndLon(String lat, String lon);
}
