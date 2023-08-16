package ru.practicum.main.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.CategoriesDto;
import ru.practicum.main.mapper.CategoriesMapper;
import ru.practicum.main.model.Categories;
import ru.practicum.main.service.AdminCategoriesService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminCategoriesControllerTest {

    @Mock
    private AdminCategoriesService service;

    @InjectMocks
    private AdminCategoriesController controller;

    private Categories categories = Categories.builder()
            .id(1)
            .name("cat")
            .build();

    @Test
    void createCategories() {
        CategoriesDto categoriesDto = CategoriesMapper.toCategoriesDto(categories);

        when(service.createCategories(any())).thenReturn(categories);

        CategoriesDto newCat = controller.createCategories(categoriesDto);

        assertEquals(newCat, categoriesDto);

    }

    @Test
    void deleteCategories() {
        controller.deleteCategories(categories.getId());

        verify(service, times(1)).deleteCategories(categories.getId());
    }

    @Test
    void patchCategories() {
        Categories newCat = categories;
        newCat.setName("newCat");
        CategoriesDto categoriesDto = CategoriesMapper.toCategoriesDto(newCat);

        when(service.patchCategories(anyLong(), any())).thenReturn(newCat);

        CategoriesDto categoriesDto1 = controller.patchCategories(categories.getId(), categoriesDto);

        assertEquals(categoriesDto1, categoriesDto);
    }
}