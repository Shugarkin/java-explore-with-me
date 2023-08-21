package ru.practicum.main.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentShortDto {

    private long id;

    private String text;

    private UserDto author;

    private String createTime;
}
