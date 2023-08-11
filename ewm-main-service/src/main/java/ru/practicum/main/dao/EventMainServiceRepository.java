package ru.practicum.main.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventMainServiceRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiatorId(long userId, Pageable pageable);


    Optional<Event> findByIdAndInitiatorId(long eventId, long userId);
}
