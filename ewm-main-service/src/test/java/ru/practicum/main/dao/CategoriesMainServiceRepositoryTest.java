//package ru.practicum.main.dao;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import ru.practicum.main.model.Categories;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class CategoriesMainServiceRepositoryTest {
//
//    @Autowired
//    private CategoriesMainServiceRepository repository;
//
//    private Categories categories = Categories.builder()
//            .name("asdsa")
//            .build();
//
//    @AfterEach
//    void after() {
//        repository.deleteAll();
//    }
//
//    @Test
//    void findAllCategories() {
//        repository.save(categories);
//        Pageable pageable = PageRequest.of(0, 10);
//        List<Categories> allCategories = repository.findAllCategories(pageable);
//
//        assertEquals(allCategories, List.of(categories));
//    }
//}