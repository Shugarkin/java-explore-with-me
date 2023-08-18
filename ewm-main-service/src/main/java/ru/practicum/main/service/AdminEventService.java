package ru.practicum.main.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.State;
import ru.practicum.main.model.AdminEvent;
import ru.practicum.main.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    @Transactional
    Event patchAdminEvent(long eventId, AdminEvent eventNew);

    List<Event> getAdminEvents(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);
}
