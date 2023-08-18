package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CompilationsDto;
import ru.practicum.main.mapper.CompilationMapper;
import ru.practicum.main.model.CompilationShort;
import ru.practicum.main.service.PublicCompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PublicCompilationsController {

    private final PublicCompilationService service;

    @GetMapping("/{compId}")
    public CompilationsDto getCompilationById(@PathVariable long compId) {
        CompilationShort compilationShort = service.getCompilationById(compId);
        return CompilationMapper.toCompilationsDto(compilationShort);
    }

    @GetMapping
    public List<CompilationsDto> getCompilation(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                @RequestParam(defaultValue = "10") @Positive int size) {
        List<CompilationShort> list = service.getCompilation(pinned, from, size);
        return CompilationMapper.toListCompilationDto(list);
    }
}
