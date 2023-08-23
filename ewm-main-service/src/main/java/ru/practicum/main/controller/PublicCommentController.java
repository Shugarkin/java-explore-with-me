package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.dto.CommentDto;
import ru.practicum.main.dto.CommentShortDto;
import ru.practicum.main.mapper.CommentMapper;
import ru.practicum.main.model.Comment;
import ru.practicum.main.service.PublicCommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublicCommentController {

    private final PublicCommentService service;

    @GetMapping("/comment/{comId}")
    public CommentDto getComment(@PathVariable long comId) {
        Comment comment = service.getComment(comId);
        return CommentMapper.toCommentDto(comment);
    }

    @GetMapping("/events/{eventId}/comment")
    public List<CommentShortDto> getCommentsByEvent(@PathVariable long eventId,
                                                    @RequestParam(defaultValue = "0") int from,
                                                    @RequestParam(defaultValue = "10") int size) {
        List<Comment> list = service.getCommentsByEvent(eventId, from, size);
        return CommentMapper.toListCommentShortDto(list);
    }
}
