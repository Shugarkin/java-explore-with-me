package ru.practicum.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatUniqueOrNotDto;
import ru.practicum.mapper.StatMapper;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatServiceControllerTest {

    @Mock
    private StatService statService;

    @InjectMocks
    private StatServiceController statServiceController;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Stat stat = Stat.builder()
            .ip("85.249.18.233")
            .uri("/events")
            .timestamp(LocalDateTime.parse("2035-05-05 00:00:00",formatter))
            .app("ewm-main-service")
            .build();

    @Test
    void postStatEvent() {
        StatDto statDto = StatMapper.toStatDto(stat);

        when(statService.postStat(stat)).thenReturn(stat);

        StatDto newStatDto = statServiceController.postStatEvent(statDto);

        assertEquals(newStatDto, statDto);
    }

    @Test
    void getStatEvent() {
        LocalDateTime startDate = LocalDateTime.parse("2020-05-05 00:00:00", formatter);
        LocalDateTime endDate = LocalDateTime.parse("2035-05-05 00:00:00", formatter);
        List<String> uris = List.of();
        boolean unique = false;
        StatUniqueOrNot statUniqueOrNot = StatMapper.toUnique(stat);


        when(statService.getStat(startDate, endDate, uris, unique)).thenReturn(List.of(statUniqueOrNot));

        List<StatUniqueOrNotDto> statEvent = statServiceController.getStatEvent(startDate, endDate, uris, unique);

        List<StatUniqueOrNotDto> statUniqueOrNotDto = List.of(StatMapper.toStatDtoFromStatUnique(statUniqueOrNot));

        assertEquals(statEvent, statUniqueOrNotDto);
    }
}