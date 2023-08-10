package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoriesDto;
import ru.practicum.mapper.CategoriesMapper;
import ru.practicum.model.Categories;
import ru.practicum.service.CategoriesService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/categories")
public class PublicCategoriesController {

    private final CategoriesService service;

    @GetMapping
    public List<CategoriesDto> getListCategories(@RequestParam(defaultValue = "0") @Min(0) int from,
                                             @RequestParam(defaultValue = "10") @Min(0) int size) {
        List<Categories> list = service.getListCategories(from, size);
        return CategoriesMapper.toListCategoriesDto(list);
    }

    @GetMapping("/{catId}")
    public CategoriesDto getCategories(@PathVariable long catId) {
        Categories categories = service.getCategories(catId);
        return CategoriesMapper.toCategoriesDto(categories);
    }

}
