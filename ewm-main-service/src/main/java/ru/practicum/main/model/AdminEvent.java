package ru.practicum.main.model;

import lombok.*;
import ru.practicum.main.dto.AdminUpdateEventStatus;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AdminEvent {

    private String annotation;

    private Long category;

    private String description;

    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private String title;

    private AdminUpdateEventStatus stateAction;
}
