package ru.practicum.main.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.main.service.PublicEventService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicEventController.class)
class PublicEventControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicEventService service;


    @SneakyThrows
    @Test
    void getPublicEvents() {

        when(service.getPublicEvents(any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(List.of());

        mockMvc.perform(get("/events")).andExpect(status().isOk());

        verify(service).getPublicEvents(any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
    }

//    @SneakyThrows
//    @Test
//    void getPublicEvent() {
//        EventFull event = EventFull.builder()
//                .id(1L)
//                .eventDate(LocalDateTime.now().withNano(0).plusDays(1))
//                .description("aaaaaaaaaaaaaaaaaaaaaaaa")
//                .title("aaaaaaaaa")
//                .location(Location.builder().lat("1124").lon("1421").build())
//                .paid(false)
//                .category(Categories.builder().id(1L).name("asdsa").build())
//                .participantLimit(12)
//                .createdOn(LocalDateTime.now().withNano(0))
//                .requestModeration(false)
//                .annotation("aaaaaaaaaaaaaaaaaaaaaaaaa")
//                .initiator(User.builder().id(1L).name("asdasf").email("asdsf@asfs.ru").build())
//                .state(State.PUBLISHED)
//                .publishedOn(LocalDateTime.now().withNano(0))
//                .build();
//        when(service.getPublicEvent(anyLong(), any())).thenReturn(event);
//
//        mockMvc.perform(get("/events/{id}", 1L)).andExpect(status().isOk());
//
//        verify(service).getPublicEvent(anyLong(), any());
//    }
}