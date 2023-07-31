package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class StatDto {

    private String app;

    private String uri;

    private String ip;

    private String timestamp;
}
