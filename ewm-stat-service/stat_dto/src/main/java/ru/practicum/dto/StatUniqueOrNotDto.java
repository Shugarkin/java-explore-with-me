package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class StatUniqueOrNotDto {

    private String app;

    private String uri;

    private Long hits;
}
