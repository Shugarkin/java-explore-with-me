package ru.practicum.main.dto;

import lombok.*;
import ru.practicum.main.model.EventCommentDto;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;

    private String text;

    private UserDto author;

    private EventCommentDto event;

    private String createTime;

    private String patchTime;
}
