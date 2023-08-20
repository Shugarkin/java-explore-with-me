package ru.practicum.main.dto;

import lombok.*;
import ru.practicum.dto.Status;

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
