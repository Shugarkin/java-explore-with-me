package ru.practicum.main.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.model.Comment;

import java.util.List;

public interface CommentMainServiceRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByEventId(long eventId, Pageable pageable);
}
