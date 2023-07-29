package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.StatEventDto;
import ru.practicum.model.StatEvent;

@UtilityClass
public class StatEventMapper {

    public StatEvent toStatEvent(StatEventDto statEventDto) {
        return StatEvent.builder()
                .app(statEventDto.getApp())
                .uri(statEventDto.getUri())
                .ip(statEventDto.getIp())
                .build();
    }

    public StatEventDto toStatEventDto(StatEvent statEvent) {
        return StatEventDto.builder()
                .app(statEvent.getApp())
                .ip(statEvent.getIp())
                .timestamp(statEvent.getTimestamp())
                .uri(statEvent.getUri())
                .build();
    }
}
