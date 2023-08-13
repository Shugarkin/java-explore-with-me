package ru.practicum.main.service;

import ru.practicum.dto.EventReceivedDto;
import ru.practicum.main.model.AdminEvent;
import ru.practicum.main.model.Event;
import ru.practicum.main.model.EventFull;
import ru.practicum.main.model.UpdateEvent;

import java.util.List;

public interface EventService {
    Event createEvent(long userId, Event event);

    List<EventFull> getEventByUserId(long userId, int from, int size);

    EventFull getEventByUserIdAndEventId(long userId, long eventId);

    EventFull patchEvent(long userId, long eventId, UpdateEvent updateEvent);

    EventFull patchAdminEvent(long eventId, AdminEvent receivedDto);
}
