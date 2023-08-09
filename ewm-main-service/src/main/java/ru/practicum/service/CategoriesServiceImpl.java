package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.CategoriesMainServiceRepository;
import ru.practicum.dto.CategoriesDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.Categories;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesMainServiceRepository repository;

    @Override
    @Transactional
    public Categories createCategories(Categories categories) {
        return repository.save(categories);
    }

    @Override
    @Transactional
    public void deleteCategories(long catId) {
        boolean answer = repository.existsById(catId);
        if (answer) {
            repository.deleteById(catId);
        } else {
            throw new NotFoundException("Нет данной категории");
        }
    }

    @Transactional
    @Override
    public Categories patchCategories(long catId, Categories categoriesDto) {
        Categories categories = repository.findById(catId).orElseThrow(() -> new NotFoundException("Жанной категории нет"));
        categories.setName(categoriesDto.getName());
        return categories;
    }


}
