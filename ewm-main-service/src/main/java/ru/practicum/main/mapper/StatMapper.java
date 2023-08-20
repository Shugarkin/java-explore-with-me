package ru.practicum.main.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.StatUniqueOrNotDto;
import ru.practicum.dto.Stat;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class StatMapper {

    public Stat toStat(StatUniqueOrNotDto statUniqueOrNotDto) {
        return Stat.builder()
                .hits(statUniqueOrNotDto.getHits())
                .app(statUniqueOrNotDto.getApp())
                .uri(statUniqueOrNotDto.getUri())
                .build();
    }

    public List<Stat> toListStat(List<StatUniqueOrNotDto> list) {
        return list.stream().map(StatMapper::toStat).collect(Collectors.toList());
    }
}
