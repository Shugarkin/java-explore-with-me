//package ru.practicum.main.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.practicum.main.dao.UserMainServiceRepository;
//import ru.practicum.main.model.User;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//
//    @Mock
//    private UserMainServiceRepository repository;
//
//    @InjectMocks
//    private UserServiceImpl service;
//
//    @Test
//    void createUser() {
//        service.createUser(User.builder().build());
//        verify(repository).save(User.builder().build());
//    }
//
//    @Test
//    void deleteUser() {
//        when(repository.existsById(anyLong())).thenReturn(true);
//
//        service.deleteUser(1L);
//
//        verify(repository).deleteById(1L);
//    }
//
//    @Test
//    void getUsers() {
//        when(repository.findAllByIds(any(), any())).thenReturn(List.of());
//
//        List<User> users = service.getUsers(List.of(1L), 0, 10);
//        assertEquals(users, List.of());
//    }
//}