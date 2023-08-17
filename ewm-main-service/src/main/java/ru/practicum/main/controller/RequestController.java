package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.RequestDto;
import ru.practicum.main.mapper.RequestMapper;
import ru.practicum.main.model.Request;
import ru.practicum.main.service.RequestService;

import java.util.List;

@RestController
@Validated
@RequestMapping("/users/")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService service;

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto createRequest(@PathVariable @Nullable Long userId, @RequestParam @Nullable Long eventId) {
        Request request = service.createRequest(userId, eventId);
        return RequestMapper.toRequestDto(request);
    }

    @GetMapping("/{userId}/requests")
    public List<RequestDto> getRequests(@PathVariable @Nullable Long userId) {
        List<Request> requests = service.getRequests(userId);
        return RequestMapper.toListRequestDto(requests);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public RequestDto canselRequest(@PathVariable @Nullable Long userId, @PathVariable @Nullable Long requestId) {
        Request request = service.canselRequest(userId, requestId);
        return RequestMapper.toRequestDto(request);
    }
}
