package ru.practicum.model;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class StatUniqueOrNot {

    private String app;

    private String uri;

    private long hits;
}
