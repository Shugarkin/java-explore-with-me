package ru.practicum.service;

import ru.practicum.model.Event;
import ru.practicum.model.EventFull;

import java.util.List;

public interface EventService {
    Event createEvent(long userId, Event event);

    List<EventFull> getEventByUserId(long userId, int from, int size);
}
