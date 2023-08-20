package ru.practicum.main.model;

import lombok.*;
import ru.practicum.main.dto.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EventFullWithComment {
    private Long id;

    private String annotation;

    private Categories category;

    private String createdOn;

    private String description;

    private String eventDate;

    private User initiator;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private State state;

    private String title;

    private Long views;

    private Long confirmedRequests;

    private String publishedOn;

    private List<Comment> comments;
}
