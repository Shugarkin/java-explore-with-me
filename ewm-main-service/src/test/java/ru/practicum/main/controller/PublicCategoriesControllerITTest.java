package ru.practicum.main.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.main.model.Categories;
import ru.practicum.main.service.PublicCategoriesService;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicCategoriesController.class)
class PublicCategoriesControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicCategoriesService service;

    @SneakyThrows
    @Test
    void getListCategories() {
        when(service.getListCategories(0, 10)).thenReturn(List.of());

        mockMvc.perform(get("/categories", 0, 10)).andExpect(status().isOk());

        verify(service).getListCategories( 0, 10);
    }

    @SneakyThrows
    @Test
    void getCategories() {

        when(service.getCategories(1L)).thenReturn(Categories.builder().build());

        mockMvc.perform(get("/categories/{catId}", 1L)).andExpect(status().isOk());

        verify(service).getCategories( 1L);
    }
}