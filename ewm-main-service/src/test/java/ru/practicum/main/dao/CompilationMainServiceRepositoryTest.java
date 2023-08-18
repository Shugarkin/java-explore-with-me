//package ru.practicum.main.dao;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import ru.practicum.main.model.Compilations;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class CompilationMainServiceRepositoryTest {
//
//    @Autowired
//    private CompilationMainServiceRepository repository;
//
////    private Compilations compilations = Compilations.builder()
////            .events(List.of())
////            .title("sadsa")
////            .pinned(false)
////            .build();
////
////    @AfterEach
////    void after() {
////        repository.deleteAll();
////    }
////
////    @Test
////    void findAllByPinned() {
////        repository.save(compilations);
////        Pageable pageable = PageRequest.of(0, 10);
////        List<Compilations> allByPinned = repository.findAllByPinned(false, pageable);
////
////        assertEquals(allByPinned, List.of(compilations));
////    }
//}