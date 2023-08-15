package ru.practicum.main.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilation {

    private List<Long> events;

    private Boolean pinned;

    private String title;
}
