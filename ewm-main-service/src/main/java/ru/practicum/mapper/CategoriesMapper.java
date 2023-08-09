package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.CategoriesDto;
import ru.practicum.model.Categories;

@UtilityClass
public class CategoriesMapper {

    public Categories toCategories(CategoriesDto categoriesDto) {
        return Categories.builder()
                .name(categoriesDto.getName())
                .build();
    }

    public CategoriesDto toCategoriesDto(Categories categories) {
        return CategoriesDto.builder()
                .id(categories.getId())
                .name(categories.getName())
                .build();
    }
}
