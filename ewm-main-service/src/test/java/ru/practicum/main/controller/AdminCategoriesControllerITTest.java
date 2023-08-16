package ru.practicum.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.CategoriesDto;
import ru.practicum.main.mapper.CategoriesMapper;
import ru.practicum.main.model.Categories;
import ru.practicum.main.service.AdminCategoriesService;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminCategoriesController.class)
class AdminCategoriesControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminCategoriesService service;

    private Categories categories = Categories.builder()
            .id(1)
            .name("cat")
            .build();

    @SneakyThrows
    @Test
    void createCategories() {
        CategoriesDto categoriesDto = CategoriesMapper.toCategoriesDto(categories);

        when(service.createCategories(any())).thenReturn(categories);

        String newCategories = mockMvc.perform(post("/admin/categories", categoriesDto)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categoriesDto))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(newCategories, objectMapper.writeValueAsString(categoriesDto));
    }

    @Test
    void deleteCategories() {
        service.deleteCategories(categories.getId());
        verify(service).deleteCategories(categories.getId());
    }

    @SneakyThrows
    @Test
    void patchCategories() {
        Categories newCat = categories;
        newCat.setName("newCat");
        CategoriesDto categoriesDto = CategoriesMapper.toCategoriesDto(newCat);

        when(service.patchCategories(anyLong(), any())).thenReturn(newCat);

        String newCategories = mockMvc.perform(patch("/admin/categories/{catId}", categories.getId(), categoriesDto)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categoriesDto))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(newCategories, objectMapper.writeValueAsString(categoriesDto));
    }
}