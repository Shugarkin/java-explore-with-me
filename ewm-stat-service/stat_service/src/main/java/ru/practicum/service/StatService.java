package ru.practicum.service;

import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    Stat postStat(Stat stat);

    List<StatUniqueOrNot> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
