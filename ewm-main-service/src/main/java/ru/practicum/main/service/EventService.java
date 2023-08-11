package ru.practicum.main.service;

import ru.practicum.main.model.Event;
import ru.practicum.main.model.EventFull;

import java.util.List;

public interface EventService {
    Event createEvent(long userId, Event event);

    List<EventFull> getEventByUserId(long userId, int from, int size);

    EventFull getEventByUserIdAndEventId(long userId, long eventId);
}
