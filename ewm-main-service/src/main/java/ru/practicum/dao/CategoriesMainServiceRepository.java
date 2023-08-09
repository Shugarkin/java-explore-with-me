package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Categories;

public interface CategoriesMainServiceRepository extends JpaRepository<Categories, Long> {
}
