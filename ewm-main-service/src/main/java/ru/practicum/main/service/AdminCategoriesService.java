package ru.practicum.main.service;

import ru.practicum.main.model.Categories;

public interface AdminCategoriesService {
    Categories createCategories(Categories categories);

    void deleteCategories(long catId);

    Categories patchCategories(long catId, Categories categoriesDto);

}
