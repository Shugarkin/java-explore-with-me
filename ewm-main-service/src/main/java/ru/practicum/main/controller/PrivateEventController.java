package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.*;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.mapper.RequestMapper;
import ru.practicum.main.model.EventFull;
import ru.practicum.main.model.Request;
import ru.practicum.main.model.RequestShortUpdate;
import ru.practicum.main.service.PrivateEventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users")
public class PrivateEventController {

    private final PrivateEventService service;

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable long userId, @Validated(Marker.Create.class) @RequestBody  EventReceivedDto eventReceivedDto) {
        EventFull event = EventMapper.toEventFull(service.createEvent(userId, EventMapper.toEvent(eventReceivedDto)), 0L, 0L);
        return EventMapper.toEventFullDto(event);
    }

    @GetMapping("/{userId}/events")
    public List<EventFullDto> getEventByUserId(@PathVariable long userId,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                               @RequestParam(defaultValue = "10") @Positive int size) {
        List<EventFull> list = service.getEventByUserId(userId, from, size);
        return EventMapper.toListEventFullDto(list);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventByUserIdAndEventId(@PathVariable long userId, @PathVariable long eventId) {
        EventFull event = service.getEventByUserIdAndEventId(userId, eventId);
        return EventMapper.toEventFullDto(event);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto patchEvent(@PathVariable long userId, @PathVariable long eventId,
                                   @RequestBody @Validated(Marker.Update.class) UpdateEventDto receivedDto) {
        EventFull eventFull = service.patchEvent(userId, eventId, EventMapper.toEventFromUpdateEvent(receivedDto));
        return EventMapper.toEventFullDto(eventFull);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<RequestDto> getRequestByUserIdAndEventId(@PathVariable long userId, @PathVariable long eventId) {
        List<Request> list = service.getRequestByUserIdAndEventId(userId, eventId);
        return RequestMapper.toListRequestDto(list);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public RequestShortUpdateDto patchRequestByOwnerUser(@PathVariable long userId, @PathVariable long eventId, @RequestBody RequestShortDto shortDto) {
        RequestShortUpdate requestShort = service.patchRequestByOwnerUser(userId, eventId, RequestMapper.toRequestShort(shortDto));
        return RequestMapper.toRequestShortUpdateDto(requestShort);
    }
}
