package ru.practicum.main.service;

import ru.practicum.main.model.Request;

import java.util.List;

public interface RequestService {
    Request createRequest(Long userId, Long eventId);

    List<Request> getRequests(Long userId);

    Request canselRequest(Long userId, Long requestId);
}
