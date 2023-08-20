package ru.practicum.main.service;

import ru.practicum.main.model.EventFullWithComment;
import ru.practicum.main.model.EventShort;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventService {
    List<EventShort> getPublicEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                     LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request);

    EventFullWithComment getPublicEvent(long id, HttpServletRequest request);
}
