package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoriesDto;
import ru.practicum.dto.Marker;
import ru.practicum.mapper.CategoriesMapper;
import ru.practicum.model.Categories;
import ru.practicum.service.CategoriesService;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/admin")
public class AdminCategoriesController {

    private final CategoriesService service;

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriesDto createCategories(@RequestBody @Validated(Marker.Create.class) CategoriesDto categoriesDto) {
        Categories categories = service.createCategories(CategoriesMapper.toCategories(categoriesDto));
        return CategoriesMapper.toCategoriesDto(categories);
    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategories(@PathVariable long catId ) {
        service.deleteCategories(catId);
    }

    @PatchMapping("/categories/{catId}")
    public CategoriesDto patchCategories(@PathVariable("catId") long catId, @RequestBody @Validated(Marker.Update.class) CategoriesDto categoriesDto) {
        CategoriesDto categories = CategoriesMapper.toCategoriesDto(service.patchCategories(catId, CategoriesMapper.toCategories(categoriesDto)));
        return categories;
    }


}
