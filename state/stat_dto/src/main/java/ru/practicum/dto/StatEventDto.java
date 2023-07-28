package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
public class StatEventDto {

    private String app;

    private String uri;

    private String ip;

    private LocalDateTime timestamp;

}
