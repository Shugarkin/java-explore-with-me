package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.dao.CategoriesMainServiceRepository;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.model.Categories;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AdminCategoriesServiceImpl implements AdminCategoriesService {

    private final CategoriesMainServiceRepository repository;

    private final EventMainServiceRepository eventMainServiceRepository;

    @Override
    @Transactional
    public Categories createCategories(Categories categories) {
        log.info("create new categories");
        return repository.save(categories);
    }

    @Override
    @Transactional
    public void deleteCategories(long catId) {
        if (eventMainServiceRepository.existsByCategoryId(catId)) {
            throw new ConflictException("Нельзя удалить категорию с ивентами");
        }

        boolean answer = repository.existsById(catId);
        if (answer) {
            log.info("delete categories");
            repository.deleteById(catId);
        } else {
            throw new NotFoundException("Нет данной категории");
        }
    }

    @Transactional
    @Override
    public Categories patchCategories(long catId, Categories categoriesDto) {
        Categories categories = repository.findById(catId).orElseThrow(() -> new NotFoundException("Данной категории нет"));

        categories.setName(categoriesDto.getName());
        log.info("patch categories");
        return categories;
    }




}
