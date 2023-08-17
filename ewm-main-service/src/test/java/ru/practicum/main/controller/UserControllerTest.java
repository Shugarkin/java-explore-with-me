package ru.practicum.main.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.AdminUserDto;
import ru.practicum.dto.UserDtoReceived;
import ru.practicum.main.mapper.UserMapper;
import ru.practicum.main.model.User;
import ru.practicum.main.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    private User user = User.builder().id(1L).name("asdas").email("asfas@yande.ru").build();

    @Test
    void createUser() {
        when(service.createUser(any())).thenReturn(user);

        AdminUserDto asdsafasf = controller.createUser(UserDtoReceived.builder().name("asdsafasf").email("asdasd@adsafaa.ru").build());

        assertEquals(asdsafasf, UserMapper.toAdminUserDto(user));
    }

    @Test
    void deleteUser() {
        controller.deleteUser(1L);
        verify(service).deleteUser(1L);
    }

    @Test
    void getUsers() {
        when(service.getUsers(any(), anyInt(), anyInt())).thenReturn(List.of(user));

        List<AdminUserDto> users = controller.getUsers(List.of(), 0, 10);

        assertEquals(users, UserMapper.toListAdminUserDto(List.of(user)));
    }
}