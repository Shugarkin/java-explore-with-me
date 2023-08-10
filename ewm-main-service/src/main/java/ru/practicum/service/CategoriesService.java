package ru.practicum.service;

import ru.practicum.dto.CategoriesDto;
import ru.practicum.model.Categories;

import java.util.List;

public interface CategoriesService {
    Categories createCategories(Categories categories);

    void deleteCategories(long catId);

    Categories patchCategories(long catId, Categories categoriesDto);

    List<Categories> getListCategories(int from, int size);

    Categories getCategories(long catId);
}
