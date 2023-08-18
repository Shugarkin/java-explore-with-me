package ru.practicum.main.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.dto.CategoriesDto;
import ru.practicum.main.model.Categories;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CategoriesDto> toListCategoriesDto(List<Categories> list) {
        return list.stream().map(CategoriesMapper::toCategoriesDto).collect(Collectors.toList());
    }
}
