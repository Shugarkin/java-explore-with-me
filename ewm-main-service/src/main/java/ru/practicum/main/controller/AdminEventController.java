package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.AdminEventReceivedDto;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventReceivedDto;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.EventFull;
import ru.practicum.main.service.EventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService service;

    @PatchMapping("/{eventId}")
    public EventFullDto patchAdminEvent(@PathVariable long eventId, @RequestBody AdminEventReceivedDto adminEvent) {
        EventFull eventFull = service.patchAdminEvent(eventId, EventMapper.toAdminEventFromAdminDto(adminEvent));
        return EventMapper.toEventFullDto(eventFull);
    }

    @GetMapping
    public List<EventFullDto> getAdminEvents(@RequestParam List<Long> users, @RequestParam List<String> states, @RequestParam List<Long> categories,
                                             @RequestParam String rangeStart, @RequestParam String rangeEnd, @RequestParam(defaultValue = "0") int from,
                                             @RequestParam(defaultValue = "10") int size) {
        List<EventFull> list = service.getAdminEvents(users, states, categories, rangeStart, rangeEnd, from, size);
        return EventMapper.toListEventFullDto(list);
    }
}
