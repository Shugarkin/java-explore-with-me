package ru.practicum.service;

import ru.practicum.dto.CategoriesDto;
import ru.practicum.model.Categories;

public interface CategoriesService {
    Categories createCategories(Categories categories);

    void deleteCategories(long catId);

    Categories patchCategories(long catId, Categories categoriesDto);
}
