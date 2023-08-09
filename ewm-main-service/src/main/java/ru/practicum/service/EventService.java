package ru.practicum.service;

import ru.practicum.model.Event;
import ru.practicum.model.EventFull;

public interface EventService {
    Event createEvent(long userId, Event event);
}
