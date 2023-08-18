package ru.practicum.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.main.service.AdminEventService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminEventController.class)
class AdminEventControllerITTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminEventService service;

//    private AdminEventReceivedDto adminEventReceivedDto = AdminEventReceivedDto.builder()
//            .category(1L)
//            .annotation("asdssssssssssssssssssssss")
//            .eventDate(LocalDateTime.now().plusDays(1).withNano(0))
//            .title("asdsa")
//            .description("sssssssssssssssssssssssss")
//            .paid(false)
//            .location(null)
//            .participantLimit(12)
//            .requestModeration(false)
//            .stateAction(AdminUpdateEventStatus.PUBLISH_EVENT)
//            .build();
//
//    private EventFull eventFull = EventFull.builder()
//            .annotation("asdssssssssssssssssssssss")
//            .eventDate(LocalDateTime.now().plusDays(1).withNano(0))
//            .title("asdsa")
//            .description("sssssssssssssssssssssssss")
//            .paid(false)
//            .location(Location.builder().id(1L).lat("123").lon("1342").build())
//            .category(Categories.builder().id(1).name("14312").build())
//            .initiator(User.builder().name("qew").id(1L).build())
//            .participantLimit(12)
//            .requestModeration(false)
//            .createdOn(LocalDateTime.now().plusDays(1).withNano(0))
//            .state(State.PUBLISHED)
//            .build();
//
//    @SneakyThrows
//    @Test
//    void patchAdminEvent() {
//        when(service.patchAdminEvent(anyLong(), any())).thenReturn(eventFull);
//
//        String newEv = mockMvc.perform(patch("/admin/events/{eventId}", 1, adminEventReceivedDto)
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(adminEventReceivedDto))
//                        .characterEncoding(StandardCharsets.UTF_8))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        assertEquals(objectMapper.writeValueAsString(eventFull), objectMapper.writeValueAsString(eventFull));
//    }

    @SneakyThrows
    @Test
    void getAdminEvents() {

        mockMvc.perform(get("/admin/events"))
                .andExpect(status().isOk());

        verify(service).getAdminEvents(null, null, null, null, null, 0, 10);
    }
}