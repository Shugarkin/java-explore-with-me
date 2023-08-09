package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.LocationDto;
import ru.practicum.model.Location;

@UtilityClass
public class LocationMapper {
    public Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .lon(locationDto.getLon())
                .lat(locationDto.getLat())
                .build();
    }
}
