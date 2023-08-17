package ru.practicum.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EventShortDto {
    private Long id;

    private String annotation;

    private CategoriesDto category;

    private Long confirmedRequests;

    private String eventDate;

    private UserDto initiator;

    private Boolean paid;

    private String title;

    private Long views;
}
