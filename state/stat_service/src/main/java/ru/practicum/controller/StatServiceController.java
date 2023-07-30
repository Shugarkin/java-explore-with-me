package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.Marker;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatEventDto;
import ru.practicum.mapper.StatEventMapper;
import ru.practicum.mapper.StatMapper;
import ru.practicum.model.Stat;
import ru.practicum.model.StatEvent;
import ru.practicum.model.StatUniqueOrNot;
import ru.practicum.service.StatService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class StatServiceController {

    private final StatService statService;

    @PostMapping("/hit")
    public StatEventDto postStatEvent(@RequestBody @Validated(Marker.Create.class) StatEventDto event) {
        StatEvent statEvent = statService.postStatEvent(StatEventMapper.toStatEvent(event));
        return StatEventMapper.toStatEventDto(statEvent);
    }

    @GetMapping("/stat")
    public List<StatDto> getStatEvent(String ip, @RequestParam("start") String start, @RequestParam("end") String end,
                                      @RequestParam(defaultValue = "") List<String> uris, @RequestParam(defaultValue = "false") boolean unique) {
        List<StatUniqueOrNot> stats =  statService.getStatEvent(ip, start, end, uris, unique);
        return StatMapper.toListStatDtoFromStatUnique(stats);
    }
}
