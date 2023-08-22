package ru.practicum.main.service;

import ru.practicum.main.model.Comment;

import java.util.List;

public interface PublicCommentService {
    Comment getComment(long comId);

    List<Comment> getCommentsByEvent(long eventId, int from, int size);
}
