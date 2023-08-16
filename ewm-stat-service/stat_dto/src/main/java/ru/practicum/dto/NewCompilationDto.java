package ru.practicum.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
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
