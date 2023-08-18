package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.AdminEventReceivedDto;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.Marker;
import ru.practicum.dto.State;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.Event;
import ru.practicum.main.service.AdminEventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/events")
public class AdminEventController {

    private final AdminEventService service;

    @PatchMapping("/{eventId}")
    public EventFullDto patchAdminEvent(@PathVariable long eventId, @RequestBody @Validated({Marker.Update.class}) AdminEventReceivedDto adminEvent) {
        Event eventFull = service.patchAdminEvent(eventId, EventMapper.toAdminEventFromAdminDto(adminEvent));
        return EventMapper.toEventFullDto(eventFull);
    }

    @GetMapping
    public List<EventFullDto> getAdminEvents(@RequestParam(required = false) List<Long> users,
                                             @RequestParam(required = false) List<State> states,
                                             @RequestParam(required = false) List<Long> categories,
                                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                             @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                             @RequestParam(defaultValue = "10") @Positive int size) {
        List<Event> list = service.getAdminEvents(users, states, categories, rangeStart, rangeEnd, from, size);
        return EventMapper.toListEventFullDto(list);
    }
}
