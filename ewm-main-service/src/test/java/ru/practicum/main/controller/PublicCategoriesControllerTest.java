//package ru.practicum.main.controller;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.practicum.main.dto.CategoriesDto;
//import ru.practicum.main.model.Categories;
//import ru.practicum.main.service.PublicCategoriesService;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class PublicCategoriesControllerTest {
//
//    @Mock
//    private PublicCategoriesService service;
//
//    @InjectMocks
//    private PublicCategoriesController controller;
//
//    @Test
//    void getListCategories() {
//        when(service.getListCategories(0, 10)).thenReturn(List.of());
//
//        List<CategoriesDto> listCategories = controller.getListCategories(0, 10);
//
//        assertEquals(listCategories, List.of());
//    }
//
//    @Test
//    void getCategories() {
//        when(service.getCategories(1L)).thenReturn(Categories.builder().build());
//
//        CategoriesDto categories = controller.getCategories(1L);
//
//        assertEquals(categories, CategoriesDto.builder().build());
//    }
//}