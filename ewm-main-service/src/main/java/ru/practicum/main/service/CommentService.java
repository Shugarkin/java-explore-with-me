package ru.practicum.main.service;

import ru.practicum.main.model.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(long userId, long eventId, Comment comment);

    void deleteComment(long userId, long comId);

    Comment patchComment(long userId, long comId, Comment comment);

    Comment getComment(long comId);

    List<Comment> getCommentsByEvent(long eventId, int from, int size);
}
