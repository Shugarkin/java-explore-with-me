package ru.practicum.main.service;

import ru.practicum.dto.State;
import ru.practicum.main.model.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface PrivateEventService {
    Event createEvent(long userId, Event event);

    List<EventFull> getEventByUserId(long userId, int from, int size);

    EventFull getEventByUserIdAndEventId(long userId, long eventId);

    EventFull patchEvent(long userId, long eventId, UpdateEvent updateEvent);

    List<Request> getRequestByUserIdAndEventId(long userId, long eventId);

    RequestShortUpdate patchRequestByOwnerUser(long userId, long eventId, RequestShort requestShort);

}
