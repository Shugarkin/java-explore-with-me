package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.CommentDto;
import ru.practicum.main.dto.NewCommentDto;
import ru.practicum.main.mapper.CommentMapper;
import ru.practicum.main.model.Comment;
import ru.practicum.main.service.PrivateCommentService;

@RestController
@Validated
@RequiredArgsConstructor
public class PrivateCommentController {

    private final PrivateCommentService service;

    @PostMapping("/users/{userId}/events/{eventId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@PathVariable long userId, @PathVariable long eventId,
                                    @RequestBody @Validated NewCommentDto newCommentDto) {
        Comment comment = service.createComment(userId, eventId, CommentMapper.toComment(newCommentDto));
        return CommentMapper.toCommentDto(comment);
    }

    @DeleteMapping("/users/{userId}/comment/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long userId, @PathVariable long comId) {
        service.deleteComment(userId, comId);
    }

    @PatchMapping("/users/{userId}/comment/{comId}")
    public CommentDto patchComment(@PathVariable long userId, @PathVariable long comId,
                                   @RequestBody @Validated NewCommentDto newCommentDto) {
        Comment comment = service.patchComment(userId, comId, CommentMapper.toComment(newCommentDto));
        return CommentMapper.toCommentDto(comment);
    }

}
