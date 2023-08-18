package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.CategoriesDto;
import ru.practicum.main.mapper.CategoriesMapper;
import ru.practicum.main.model.Categories;
import ru.practicum.main.service.PublicCategoriesService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/categories")
public class PublicCategoriesController {

    private final PublicCategoriesService service;

    @GetMapping
    public List<CategoriesDto> getListCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                             @RequestParam(defaultValue = "10") @Positive int size) {
        List<Categories> list = service.getListCategories(from, size);
        return CategoriesMapper.toListCategoriesDto(list);
    }

    @GetMapping("/{catId}")
    public CategoriesDto getCategories(@PathVariable long catId) {
        Categories categories = service.getCategories(catId);
        return CategoriesMapper.toCategoriesDto(categories);
    }

}
