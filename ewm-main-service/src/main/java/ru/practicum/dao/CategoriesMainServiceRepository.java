package ru.practicum.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Categories;

import java.util.List;

public interface CategoriesMainServiceRepository extends JpaRepository<Categories, Long> {

    @Query("select cat " +
            "from Categories as cat ")
    List<Categories> findAllCategories(Pageable pageable);
}
