package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.CompilationsDto;
import ru.practicum.main.service.AdminCompilationsService;

@RestController
@Validated
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationsController {

    private final AdminCompilationsService service;

    public CompilationsDto postCompilations() {

    }
}
