package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventReceivedDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.EventFull;
import ru.practicum.service.EventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users")
public class PrivateEventController {

    private final EventService service;

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable long userId, @RequestBody EventReceivedDto eventReceivedDto) {
        EventFull event = EventMapper.toEventFull(service.createEvent(userId, EventMapper.toEvent(eventReceivedDto)), 0L, 0L);
        return EventMapper.toEventFullDto(event);
    }

    @GetMapping("/{userId}/events")
    public List<EventFullDto> getEventByUserId(@PathVariable long userId,
                                               @RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "10") int size) {
        List<EventFull> list = service.getEventByUserId(userId, from, size);
        return EventMapper.toListEventFullDto(list);
    }
}
