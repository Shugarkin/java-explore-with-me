package ru.practicum.main.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.model.Comment;
import ru.practicum.main.model.CommentCount;

import java.util.List;

public interface CommentMainServiceRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByEventId(long eventId, Pageable pageable);

    @Query("select new ru.practicum.main.model.CommentCount(c.event.id, count(c.id)) " +
            "from Comment as c " +
            "where c.event.id in ?1 " +
            "group by c.event.id")
    List<CommentCount> findAllCommentCount(List<Long> listEventId);
}
