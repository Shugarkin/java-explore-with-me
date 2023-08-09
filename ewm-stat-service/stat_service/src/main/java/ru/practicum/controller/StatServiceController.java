package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.Marker;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatUniqueOrNotDto;
import ru.practicum.mapper.StatMapper;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class StatServiceController {

    private final StatService statService;

    @PostMapping("/hit")
    public StatDto postStatEvent(@RequestBody @Validated(Marker.Create.class) StatDto stat) {
        Stat statEvent = statService.postStat(StatMapper.toStat(stat));
        log.info("create hit by uri ={}", stat.getUri());
        return StatMapper.toStatDto(statEvent);
    }

    @GetMapping("/stats")
    public List<StatUniqueOrNotDto> getStatEvent(@RequestParam("start")  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                                 @RequestParam("end")  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                                 @RequestParam(defaultValue = "") List<String> uris,
                                                 @RequestParam(defaultValue = "false") boolean unique) {
        List<StatUniqueOrNot> stats =  statService.getStat(start, end, uris, unique);
        log.info("get list stat size ={}", stats.size());
        return StatMapper.toListStatDtoFromStatUnique(stats);
    }
}
