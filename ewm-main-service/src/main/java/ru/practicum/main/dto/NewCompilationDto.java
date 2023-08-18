package ru.practicum.main.dto;

import lombok.*;
import ru.practicum.dto.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {

    private Set<Long> events = new HashSet<>();

    private Boolean pinned;

    @NotBlank(groups = {Marker.Create.class})
    @Size(max = 50, groups = {Marker.Create.class, Marker.Update.class})
    private String title;
}
