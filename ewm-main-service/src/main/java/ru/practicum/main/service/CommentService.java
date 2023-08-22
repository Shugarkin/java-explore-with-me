package ru.practicum.main.service;

import ru.practicum.main.model.Comment;
import ru.practicum.main.model.Event;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CommentService {

    Comment createComment(long userId, long eventId, Comment comment);

    void deleteComment(long userId, long comId);

    Comment patchComment(long userId, long comId, Comment comment);

    Comment getComment(long comId);

    List<Comment> getCommentsByEvent(long eventId, int from, int size);

    Map<Long, Long> getCommentCount(Collection<Event> list);
}
