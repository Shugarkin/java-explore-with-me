//package ru.practicum.main.dao;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import ru.practicum.main.model.User;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class UserMainServiceRepositoryTest {
//
//    @Autowired
//    private UserMainServiceRepository repository;
//
//    private long userId = 1L;
//
//    private User user = User.builder().id(1L).name("asdasf").email("asdsf@asfs.ru").build();
//
//    private Pageable pageable = PageRequest.of(0, 10);
//
//    @BeforeEach
//    void before() {
//        user = repository.save(user);
//        userId = user.getId();
//    }
//
//    @AfterEach
//    void after() {
//        repository.deleteAll();
//    }
//
//    @Test
//    void findAllByIds() {
//        List<User> allByIds = repository.findAllByIds(List.of(userId), pageable);
//
//        assertEquals(allByIds.size(), 1);
//    }
//
//    @Test
//    void findAllUser() {
//        List<User> allUser = repository.findAllUser(pageable);
//        assertEquals(allUser.size(), 1);
//    }
//}