package ru.practicum.main.dto;


import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    private double lat;

    private double lon;
}
