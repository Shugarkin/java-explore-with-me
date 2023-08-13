package ru.practicum.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private LocalDateTime created;

    private Long event;

    private Long id;

    private Long requester;

    private Status status;
}
