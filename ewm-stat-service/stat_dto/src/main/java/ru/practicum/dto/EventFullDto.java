package ru.practicum.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private long id;

    private String annotation;

    private CategoriesDto category;

    private LocalDateTime createdOn;

    private String description;

    private LocalDateTime eventDate;

    private UserDto initiator;

    private LocationDto location;

    private boolean paid;

    private int participantLimit;

    private boolean requestModeration;

    private State state;

    private String title;

    private long views;

    private long confirmedRequests;
}
