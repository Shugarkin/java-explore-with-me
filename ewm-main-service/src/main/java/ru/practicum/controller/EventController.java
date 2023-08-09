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

@RestController
@RequiredArgsConstructor
@Validated
public class EventController {

    private final EventService service;

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable long userId, @RequestBody EventReceivedDto eventReceivedDto) {
        EventFull event = EventMapper.toEventFull(service.createEvent(userId, EventMapper.toEvent(eventReceivedDto)), 0L, 0L);
        return EventMapper.toEventFullDto(event);
    }
}
