package ru.practicum.main.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCount {

    private Long eventId;

    private Long commentCount;

    public CommentCount(Long eventId, Long commentCount) {
        this.eventId = eventId;
        this.commentCount = commentCount;
    }
}
