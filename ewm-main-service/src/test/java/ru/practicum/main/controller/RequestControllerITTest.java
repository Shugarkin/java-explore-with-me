package ru.practicum.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.State;
import ru.practicum.dto.Status;
import ru.practicum.main.mapper.RequestMapper;
import ru.practicum.main.model.*;
import ru.practicum.main.service.AdminCompilationsService;
import ru.practicum.main.service.RequestService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RequestController.class)
class RequestControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RequestService service;

    private Request request =  Request.builder()
            .id(1L)
            .requester(User.builder().id(1L).name("asdas").email("asfas").build())
            .created(LocalDateTime.now().withNano(0))
            .event(Event.builder()
                    .id(1L)
                    .eventDate(LocalDateTime.now().withNano(0).plusDays(1))
                    .description("aaaaaaaaaaaaaaaaaaaaaaaa")
                    .title("aaaaaaaaa")
                    .location(Location.builder().lat("1124").lon("1421").build())
                    .paid(false)
                    .category(Categories.builder().id(1L).name("asdsa").build())
                    .participantLimit(12)
                    .createdOn(LocalDateTime.now().withNano(0))
                    .requestModeration(false)
                    .annotation("aaaaaaaaaaaaaaaaaaaaaaaaa")
                    .initiator(User.builder().id(1L).name("asdasf").email("asdsf@asfs.ru").build())
                    .state(State.PUBLISHED)
                    .publishedOn(LocalDateTime.now().withNano(0))
                    .build())
            .status(Status.CONFIRMED)
            .build();

    @SneakyThrows
    @Test
    void createRequest() {
        when(service.createRequest(anyLong(), anyLong())).thenReturn(request);

        String newReq = mockMvc.perform(post("/users/{userId}/requests", 1)
                        .contentType("application/json")
                        .param("eventId", String.valueOf(1L))
                        .content(objectMapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(newReq, objectMapper.writeValueAsString(RequestMapper.toRequestDto(request)));
    }

    @SneakyThrows
    @Test
    void getRequests() {
        when(service.getRequests(anyLong())).thenReturn(List.of());

        mockMvc.perform(get("/users/{userId}/requests", 1L))
                .andExpect(status().isOk());

        verify(service).getRequests(1L);

    }

    @Test
    void canselRequest() {
        service.canselRequest(1L, 1L);
        verify(service).canselRequest(1L, 1L);
    }
}