package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.StatDto;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class StatMapper {

//    public StatDto toStatDto(Stat stat) {
//        return StatDto.builder()
//                .app(stat.getEvent().getApp())
//                .hits(stat.getHits())
//                .uri(stat.getEvent().getUri())
//                .build();
//    }
//
//    public List<StatDto> toListStatDto(List<Stat> list) {
//        return list.stream().map(StatMapper::toStatDto).collect(Collectors.toList());
//    }

    public StatUniqueOrNot toUnique(Stat stat) {
        return StatUniqueOrNot.builder()
                .app(stat.getEvent().getApp())
                .uri(stat.getEvent().getUri())
                .hits(stat.getHitsUnique())
                .build();
    }

    public List<StatUniqueOrNot> toListUnique(List<Stat> list) {
        return list.stream().map(StatMapper::toUnique).collect(Collectors.toList());
    }

    public StatUniqueOrNot toNotUnique(Stat stat) {
        return StatUniqueOrNot.builder()
                .app(stat.getEvent().getApp())
                .uri(stat.getEvent().getUri())
                .hits(stat.getHits())
                .build();
    }

    public List<StatUniqueOrNot> toListNotUnique(List<Stat> list) {
        return list.stream().map(StatMapper::toNotUnique).collect(Collectors.toList());
    }

    public StatDto toStatDtoFromStatUnique(StatUniqueOrNot statUniqueOrNot) {
        return StatDto.builder()
                .app(statUniqueOrNot.getApp())
                .hits(statUniqueOrNot.getHits())
                .uri(statUniqueOrNot.getUri())
                .build();
    }

    public List<StatDto> toListStatDtoFromStatUnique(List<StatUniqueOrNot> list) {
        return list.stream().map(StatMapper::toStatDtoFromStatUnique).collect(Collectors.toList());
    }
}
