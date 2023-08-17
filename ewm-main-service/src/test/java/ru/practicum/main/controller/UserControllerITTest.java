package ru.practicum.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.UserDtoReceived;
import ru.practicum.main.model.User;
import ru.practicum.main.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService service;

    private User user = User.builder().id(1L).name("asdas").email("asfas@yande.ru").build();

    @SneakyThrows
    @Test
    void createUser() {
        when(service.createUser(any())).thenReturn(user);

        UserDtoReceived userDtoReceived = UserDtoReceived.builder().email("safasfasf@yandex.ru").name("asfdasf").build();

        String newUs = mockMvc.perform(post("/admin/users", userDtoReceived)
                        .contentType("application/json")
                        .param("eventId", String.valueOf(1L))
                        .content(objectMapper.writeValueAsString(user))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(newUs, objectMapper.writeValueAsString(user));
    }

    @Test
    void deleteUser() {
        service.deleteUser(1L);
        verify(service).deleteUser(1L);
    }

    @SneakyThrows
    @Test
    void getUsers() {
        when(service.getUsers(any(), anyInt(), anyInt())).thenReturn(List.of());

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk());

        verify(service).getUsers(List.of(), 0, 10);
    }
}