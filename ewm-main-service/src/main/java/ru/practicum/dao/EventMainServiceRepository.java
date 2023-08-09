package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Event;

public interface EventMainServiceRepository extends JpaRepository<Event, Long> {
}
