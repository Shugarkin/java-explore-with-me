package ru.practicum.main.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompilationShort {

    private Long id;

    private Boolean pinned;

    private String title;

    private List<EventShort> events;
}
