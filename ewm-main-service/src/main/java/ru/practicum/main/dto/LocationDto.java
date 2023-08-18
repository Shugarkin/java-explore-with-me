package ru.practicum.main.dto;


import lombok.*;
import ru.practicum.dto.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    @NotBlank(groups = Marker.Create.class)
    @NotNull(groups = Marker.Create.class)
    private String lat;

    @NotBlank(groups = Marker.Create.class)
    @NotNull(groups = Marker.Create.class)
    private String lon;
}
