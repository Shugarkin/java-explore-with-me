package ru.practicum.service;

import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatEventDto;
import ru.practicum.model.Stat;
import ru.practicum.model.StatEvent;

import java.util.List;

public interface StatService {

    StatEvent postStatEvent(StatEvent event);

    List<Stat> getStatEvent(String ip, String start, String end, String[] uris, boolean unique);
}
