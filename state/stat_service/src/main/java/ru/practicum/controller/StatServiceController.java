package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.Marker;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatUniqueOrNotDto;
import ru.practicum.mapper.StatMapper;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;
import ru.practicum.service.StatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class StatServiceController {

    private final StatService statService;

    @PostMapping("/hit")
    public StatDto postStatEvent(@RequestBody @Validated(Marker.Create.class) StatDto stat) {
        Stat statEvent = statService.postStatEvent(StatMapper.toStat(stat));

        return StatMapper.toStatDto(statEvent);
    }

    @GetMapping("/stats")
    public List<StatUniqueOrNotDto> getStatEvent(String ip, @RequestParam("start") String start, @RequestParam("end") String end,
                                                 @RequestParam(defaultValue = "") List<String> uris, @RequestParam(defaultValue = "false") boolean unique) {
        List<StatUniqueOrNot> stats =  statService.getStatEvent(ip, start, end, uris, unique);
        return StatMapper.toListStatDtoFromStatUnique(stats);
    }
}
