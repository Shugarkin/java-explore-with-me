package ru.practicum.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    @NotBlank(groups = Marker.Create.class)
    private String lat;

    @NotBlank(groups = Marker.Create.class)
    private String lon;
}
