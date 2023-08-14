package ru.practicum.main.service;

import ru.practicum.main.model.Categories;

import java.util.List;

public interface PublicCategoriesService {
    List<Categories> getListCategories(int from, int size);

    Categories getCategories(long catId);
}
