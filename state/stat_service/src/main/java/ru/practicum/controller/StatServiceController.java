package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.Marker;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatEventDto;
import ru.practicum.mapper.StatEventMapper;
import ru.practicum.mapper.StatMapper;
import ru.practicum.model.Stat;
import ru.practicum.model.StatEvent;
import ru.practicum.service.StatService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class StatServiceController {

    private final StatService statService;

    public StatEventDto postStatEvent(@RequestBody @Validated(Marker.Create.class) StatEventDto event) {
        StatEvent statEvent = statService.postStatEvent(StatEventMapper.toStatEvent(event));
        return StatEventMapper.toStatEventDto(statEvent);
    }

    public List<StatDto> getStatEvent(HttpServletRequest ip, String start, String end, String[] uris, boolean unique) {
        List<Stat> stats =  statService.getStatEvent(ip.getRemoteAddr(), start, end, uris, unique);
        return StatMapper.toListStatDto(stats);
    }
}
