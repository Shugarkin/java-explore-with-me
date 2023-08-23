package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.dao.CommentMainServiceRepository;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.dto.State;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.model.Comment;
import ru.practicum.main.model.Event;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PublicCommentServiceImpl implements PublicCommentService {

    private final CommentMainServiceRepository repository;

    private final EventMainServiceRepository eventMainServiceRepository;

    @Override
    public Comment getComment(long comId) {
        Comment comment = repository.findById(comId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + comId + " не найден"));
        log.info("get comment id=" + comId);
        return comment;
    }

    @Override
    public List<Comment> getCommentsByEvent(long eventId, int from, int size) {
        Event answer = eventMainServiceRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Данного события не существует"));
        if (!answer.getState().equals(State.PUBLISHED)) {
            throw new NotFoundException("Данное событие не опубликованно");
        }

        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("createTime").ascending());
        List<Comment> list = repository.findAllByEventId(eventId, pageable);
        log.info("get list comment");
        return list;
    }
}
