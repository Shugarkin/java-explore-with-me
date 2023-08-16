package ru.practicum.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompilationsDto {

    private List<EventShortDto> events;

    private Long id;

    private Boolean pinned;

    private String title;
}
