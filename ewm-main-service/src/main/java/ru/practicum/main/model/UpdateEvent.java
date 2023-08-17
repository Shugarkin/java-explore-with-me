package ru.practicum.main.model;

import lombok.*;
import ru.practicum.dto.UpdateEventStatus;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEvent {

    private String annotation;

    private Long category;

    private String description;

    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private String title;

    private UpdateEventStatus stateAction;
}
