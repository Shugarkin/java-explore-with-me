package ru.practicum.main.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.main.dao.CategoriesMainServiceRepository;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.model.Categories;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminCategoriesServiceImplTest {

    @InjectMocks
    private AdminCategoriesServiceImpl service;

    @Mock
    private CategoriesMainServiceRepository repository;

    @Mock
    private EventMainServiceRepository eventMainServiceRepository;


    private Categories categories = Categories.builder().name("asf").id(1L).build();

    @Test
    void createCategories() {
        when(repository.save(any())).thenReturn(categories);

        Categories newCat = service.createCategories(categories);

        assertNotNull(newCat);

    }

    @Test
    void deleteCategories() {
        when(eventMainServiceRepository.existsByCategoryId(anyLong())).thenReturn(false);
        when(repository.existsById(anyLong())).thenReturn(true);
        service.deleteCategories(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void patchCategories() {
        when(repository.findById(1L)).thenReturn(Optional.of(categories));
        Categories newCat = service.patchCategories(1L, categories);
        assertEquals(newCat, categories);
    }
}