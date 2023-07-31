package ru.practicum.service;

import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.util.List;

public interface StatService {

    Stat postStat(Stat stat);

    List<StatUniqueOrNot> getStat(String start, String end, List<String> uris, boolean unique);
}
