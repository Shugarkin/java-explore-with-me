package ru.practicum.main.service;

import ru.practicum.main.model.Comment;
import ru.practicum.main.model.Event;

import java.util.Collection;
import java.util.Map;

public interface PrivateCommentService {

    Comment createComment(long userId, long eventId, Comment comment);

    void deleteComment(long userId, long comId);

    Comment patchComment(long userId, long comId, Comment comment);

    Map<Long, Long> getCommentCount(Collection<Event> list);
}
