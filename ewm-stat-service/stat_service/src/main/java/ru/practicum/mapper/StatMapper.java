package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatUniqueOrNotDto;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class StatMapper {


    public Stat toStat(StatDto statDto) {

        return Stat.builder()
                .ip(statDto.getIp())
                .uri(statDto.getUri())
                .timestamp(statDto.getTimestamp())
                .app(statDto.getApp())
                .build();
    }

    public StatDto toStatDto(Stat stat) {
        return StatDto.builder()
                .timestamp(stat.getTimestamp())
                .app(stat.getApp())
                .uri(stat.getUri())
                .ip(stat.getIp())
                .build();
    }


    public StatUniqueOrNotDto toStatDtoFromStatUnique(StatUniqueOrNot statUniqueOrNot) {
        return StatUniqueOrNotDto.builder()
                .app(statUniqueOrNot.getApp())
                .hits(statUniqueOrNot.getHits())
                .uri(statUniqueOrNot.getUri())
                .build();
    }

    public List<StatUniqueOrNotDto> toListStatDtoFromStatUnique(List<StatUniqueOrNot> list) {
        return list.stream().map(StatMapper::toStatDtoFromStatUnique).collect(Collectors.toList());
    }
}
