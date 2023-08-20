package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.dao.CategoriesMainServiceRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.model.Categories;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PublicCategoriesServiceImpl implements PublicCategoriesService {

    private final CategoriesMainServiceRepository repository;

    @Override
    public List<Categories> getListCategories(int from, int size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("id").ascending());
        List<Categories> list = repository.findAllCategories(pageable);
        log.info("get list categories");
        return list;
    }

    @Override
    public Categories getCategories(long catId) {
        Categories categories = repository.findById(catId).orElseThrow(() -> new NotFoundException("Данной категории нет"));
        log.info("get categories");
        return categories;
    }
}
