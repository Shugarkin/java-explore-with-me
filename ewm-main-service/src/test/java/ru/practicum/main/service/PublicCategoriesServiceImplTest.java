//package ru.practicum.main.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.practicum.main.dao.CategoriesMainServiceRepository;
//import ru.practicum.main.model.Categories;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class PublicCategoriesServiceImplTest {
//
//    @Mock
//    private CategoriesMainServiceRepository repository;
//
//    @InjectMocks
//    private PublicCategoriesServiceImpl service;
//
//    @Test
//    void getListCategories() {
//        when(repository.findAllCategories(any())).thenReturn(List.of(Categories.builder().build()));
//        List<Categories> listCategories = service.getListCategories(0, 10);
//        assertEquals(listCategories.size(), 1);
//    }
//
//    @Test
//    void getCategories() {
//        when(repository.findById(anyLong())).thenReturn(Optional.of(Categories.builder().build()));
//        Categories categories = service.getCategories(1L);
//        assertNotNull(categories);
//    }
//}