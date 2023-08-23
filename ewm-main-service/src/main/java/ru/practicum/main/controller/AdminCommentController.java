package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.CommentDto;
import ru.practicum.main.mapper.CommentMapper;
import ru.practicum.main.model.Comment;
import ru.practicum.main.service.AdminCommentService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class AdminCommentController {

    private final  AdminCommentService service;

    @DeleteMapping("comment/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adminDeleteComment(@PathVariable long comId) {
        service.adminDeleteComment(comId);
    }

    @GetMapping("comment/search")
    public List<CommentDto> adminCommentSearch(@RequestParam String text) {
        List<Comment> list = service.adminCommentSearch(text);
        return CommentMapper.toListCommentDto(list);
    }

    @GetMapping("users/{userId}/comment")
    public List<CommentDto> adminUserComment(@PathVariable long userId) {
        List<Comment> list = service.adminUserComment(userId);
        return CommentMapper.toListCommentDto(list);
    }
}
