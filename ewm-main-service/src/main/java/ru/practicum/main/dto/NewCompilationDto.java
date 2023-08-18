package ru.practicum.main.dto;

import lombok.*;
import ru.practicum.dto.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {

    private List<Long> events = new ArrayList<>();

    private Boolean pinned;

    @NotBlank(groups = {Marker.Create.class})
    @Size(max = 50, groups = {Marker.Create.class, Marker.Update.class})
    private String title;
}
