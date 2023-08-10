package ru.practicum.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Event;

import java.util.List;

public interface EventMainServiceRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiatorId(long userId, Pageable pageable);
}
