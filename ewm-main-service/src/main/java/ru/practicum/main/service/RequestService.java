package ru.practicum.main.service;

import ru.practicum.main.model.Request;

import java.util.List;

public interface RequestService {
    Request createRequest(long userId, long eventId);

    List<Request> getRequests(long userId);
}
