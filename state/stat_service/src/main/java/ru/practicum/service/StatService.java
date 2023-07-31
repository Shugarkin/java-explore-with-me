package ru.practicum.service;

import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.util.List;

public interface StatService {

    Stat postStatEvent(Stat event);

    List<StatUniqueOrNot> getStatEvent(String ip, String start, String end, List<String> uris, boolean unique);
}
