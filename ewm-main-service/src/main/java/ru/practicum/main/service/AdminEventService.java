package ru.practicum.main.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.State;
import ru.practicum.main.model.AdminEvent;
import ru.practicum.main.model.EventFull;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    @Transactional
    EventFull patchAdminEvent(long eventId, AdminEvent eventNew);

    List<EventFull> getAdminEvents(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);
}
