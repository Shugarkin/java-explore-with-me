package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.StatDto;
import ru.practicum.model.Stat;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class StatMapper {

    public StatDto toStatDto(Stat stat) {
        return StatDto.builder()
                .app(stat.getStatEventId().getApp())
                .hits(stat.getHits())
                .uri(stat.getStatEventId().getUri())
                .build();
    }

    public List<StatDto> toListStatDto(List<Stat> list) {
        return list.stream().map(StatMapper::toStatDto).collect(Collectors.toList());
    }
}
