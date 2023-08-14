package ru.practicum.main.service;

import ru.practicum.dto.StatDto;
import ru.practicum.main.model.Event;

import java.util.List;
import java.util.Map;

public interface StatService {

    Map<Long, Long> toConfirmedRequest(List<Event> list);

    Map<Long, Long> toView(List<Event> list);

    void addHits(StatDto statDto);
}
