package ru.practicum.main.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.dto.State;
import ru.practicum.main.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventMainServiceRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiatorId(long userId, Pageable pageable);


    Optional<Event> findByIdAndInitiatorId(long eventId, long userId);

    @Query("select event " +
            "from Event as event " +
            "where (event.initiator.id in ?1 or ?1 is null) " +
            "and (event.state in ?2 or ?2 is null) " +
            "and (event.category.id in ?3 or ?3 is null) " +
            "and (event.eventDate > ?4 or ?4 is null) " +
            "and (event.eventDate < ?5 or ?5 is null) ")

    List<Event> findAllByParam(List<Long> users, List<State> states, List<Long> categories, LocalDateTime start, LocalDateTime end, Pageable pageable);

    boolean existsByIdAndInitiatorId(long eventId, long userId);

    @Query("select event " +
            "from Event as event " +
            "where ((?1 is null) or ((lower(event.annotation) like concat('%', lower(?1), '%')) or (lower(event.description) like concat('%', lower(?1), '%')))) " +
            "and (event.category.id in ?2 or ?2 is null) " +
            "and (event.paid = ?3 or ?3 is null) " +
            "and (event.eventDate > ?4 or ?4 is null) and (event.eventDate < ?5 or ?5 is null) " +
            "and (?6 = false or (?6 = true and (event.participantLimit > 0 ))) ")
    List<Event> findAllEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                              LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Pageable pageable);

    boolean existsByCategoryId(long catId);
}
