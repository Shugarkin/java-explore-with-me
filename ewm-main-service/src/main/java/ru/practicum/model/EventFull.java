package ru.practicum.model;
import lombok.*;
import ru.practicum.dto.State;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EventFull {

    private long id;

    private String annotation;

    private Categories category;

    private LocalDateTime createdOn;

    private String description;

    private LocalDateTime eventDate;

    private User initiator;

    private Location location;

    private boolean paid;

    private int participantLimit;

    private boolean requestModeration;

    private State state;

    private String title;

    private long views;

    private long confirmedRequests;
}
