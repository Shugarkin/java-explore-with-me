package ru.practicum.main.service;

import ru.practicum.main.model.Categories;

import java.util.List;

public interface AdminCategoriesService {
    Categories createCategories(Categories categories);

    void deleteCategories(long catId);

    Categories patchCategories(long catId, Categories categoriesDto);

}
