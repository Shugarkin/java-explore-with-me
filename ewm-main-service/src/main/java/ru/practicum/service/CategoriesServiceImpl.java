package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.CategoriesMainServiceRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.Categories;

import java.util.List;

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
        Categories categories = repository.findById(catId).orElseThrow(() -> new NotFoundException("Данной категории нет"));
        categories.setName(categoriesDto.getName());
        return categories;
    }

    @Override
    public List<Categories> getListCategories(int from, int size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("id").ascending());
        List<Categories> list = repository.findAllCategories(pageable);
        return list;
    }

    @Override
    public Categories getCategories(long catId) {
        Categories categories = repository.findById(catId).orElseThrow(() -> new NotFoundException("Данной категории нет"));
        return categories;
    }


}
