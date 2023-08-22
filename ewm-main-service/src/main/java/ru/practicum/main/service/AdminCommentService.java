package ru.practicum.main.service;

import ru.practicum.main.model.Comment;

import java.util.List;

public interface AdminCommentService {
    void adminDeleteComment(long comId);

    List<Comment> adminCommentSearch(String text);

    List<Comment> adminUserComment(long userId);
}
