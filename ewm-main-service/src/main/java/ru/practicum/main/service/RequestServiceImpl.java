package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.State;
import ru.practicum.dto.Status;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.dao.RequestMainServiceRepository;
import ru.practicum.main.dao.UserMainServiceRepository;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.model.ConfirmedRequestShort;
import ru.practicum.main.model.Event;
import ru.practicum.main.model.Request;
import ru.practicum.main.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestMainServiceRepository repository;

    private final UserMainServiceRepository userMainServiceRepository;

    private final EventMainServiceRepository eventMainServiceRepository;

    @Transactional
    @Override
    public Request createRequest(long userId, long eventId) {

        User user = userMainServiceRepository.findById(userId).orElseThrow(() -> new NotFoundException("Вы не зарегестрированный пользователь"));

        Event event = eventMainServiceRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Данного ивента " + eventId + " не существует"));

        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Инициатор события не может отправлять заявку на его же событие");
        }

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Данное событие " + eventId + " еще не опубликованно");
        }

        boolean answer = repository.existsByRequesterIdAndEventId(userId, eventId);
        if (answer) {
            throw new ConflictException("Вы уже отправляли заявку на участие на данный ивент");
        }
        Status status;
        if (event.getRequestModeration().equals(Boolean.FALSE) || event.getParticipantLimit().equals(0)) {
            status = Status.CONFIRMED;
        } else {
            status = Status.PENDING;
        }

        List<ConfirmedRequestShort> requestShortList = repository.countByEventId(List.of(eventId));

        if (requestShortList.size() >= event.getParticipantLimit() && event.getParticipantLimit() != 0) {
            throw new ConflictException("Свободных мест для участия в ивенте " + eventId + " больще нет");
        }

        Request request = Request.builder()
                .requester(user)
                .event(event)
                .created(LocalDateTime.now().withNano(0))
                .status(status)
                .build();
        log.info("create request");
        return repository.save(request);
    }


    @Override
    public List<Request> getRequests(long userId) {
        List<Request> byRequesterId = repository.findAllByRequesterId(userId);
        log.info("get request");
        return byRequesterId;
    }

    @Transactional
    @Override
    public Request canselRequest(long userId, long requestId) {

        boolean answer = userMainServiceRepository.existsById(userId);
        if (!answer) {
            throw new ConflictException("Пользователя с id " + userId + " не существует");
        }
        Request request = repository.findById(requestId).orElseThrow(() -> new NotFoundException("Данного запроса не существует"));

        request.setStatus(Status.CANCELED);
        log.info("cansel request");
        return request;
    }

}
