package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatUniqueOrNotDto;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class StatMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public Stat toStat(StatDto statDto) {

        return Stat.builder()
                .ip(statDto.getIp())
                .uri(statDto.getUri())
                .timestamp(LocalDateTime.parse(statDto.getTimestamp(), formatter))
                .app(statDto.getApp())
                .build();
    }

    public StatDto toStatDto(Stat stat) {
        return StatDto.builder()
                .timestamp(stat.getTimestamp().format(formatter))
                .app(stat.getApp())
                .uri(stat.getUri())
                .ip(stat.getIp())
                .build();
    }


    public StatUniqueOrNot toUnique(Stat stat) {
        return StatUniqueOrNot.builder()
                .app(stat.getApp())
                .uri(stat.getUri())
                .hits(stat.getHitsUnique())
                .build();
    }

    public List<StatUniqueOrNot> toListUnique(List<Stat> list) {
        return list.stream().map(StatMapper::toUnique).collect(Collectors.toList());
    }

    public StatUniqueOrNot toNotUnique(Stat stat) {
        return StatUniqueOrNot.builder()
                .app(stat.getApp())
                .uri(stat.getUri())
                .hits(stat.getHits())
                .build();
    }

    public List<StatUniqueOrNot> toListNotUnique(List<Stat> list) {
        return list.stream().map(StatMapper::toNotUnique).collect(Collectors.toList());
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
