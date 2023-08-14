package ru.practicum.main.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventShort {
    private Long id;

    private String annotation;

    private Categories category;

    private Long confirmedRequests;

    private LocalDateTime eventDate;

    private User initiator;

    private Boolean paid;

    private String title;

    private Long views;
}
