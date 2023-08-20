package ru.practicum.main.service;

import ru.practicum.main.model.*;

import java.util.List;

public interface PrivateEventService {
    Event createEvent(long userId, Event event);

    List<Event> getEventByUserId(long userId, int from, int size);

    EventFullWithComment getEventByUserIdAndEventId(long userId, long eventId);

    Event patchEvent(long userId, long eventId, UpdateEvent updateEvent);

    List<Request> getRequestByUserIdAndEventId(long userId, long eventId);

    RequestShortUpdate patchRequestByOwnerUser(long userId, long eventId, RequestShort requestShort);

}
