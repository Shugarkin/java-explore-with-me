package ru.practicum.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.*;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.mapper.RequestMapper;
import ru.practicum.main.model.*;
import ru.practicum.main.service.PrivateEventService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrivateEventController.class)
class PrivateEventControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PrivateEventService service;

    private EventReceivedDto eventReceivedDto = EventReceivedDto.builder()
            .eventDate(LocalDateTime.now().withNano(0).plusDays(1))
            .description("aaaaaaaaaaaaaaaaaaaaaaaa")
            .title("aaaaaaaaa")
            .location(LocationDto.builder().lat("1124").lon("1421").build())
            .paid(false)
            .category(1L)
            .participantLimit(12)
            .requestModeration(false)
            .annotation("aaaaaaaaaaaaaaaaaaaaaaaaa")
            .build();

    private Event event = Event.builder()
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
            .build();

    @SneakyThrows
    @Test
    void createEvent() {
        EventFull eventFull = EventMapper.toEventFull(event, 0L, 0L);
        when(service.createEvent(1L, event)).thenReturn(event);

        String newEv = mockMvc.perform(post("/users/{userId}/events", 1L, eventReceivedDto)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(eventReceivedDto))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(objectMapper.writeValueAsString(EventMapper.toEventFull(event, 0L, 0L)), objectMapper.writeValueAsString(eventFull));
    }

    @SneakyThrows
    @Test
    void getEventByUserId() {
        when(service.getEventByUserId(1L, 0, 10)).thenReturn(List.of(EventMapper.toEventFull(event, 0L, 0L)));

        mockMvc.perform(get("/users/{userId}/events", 1L)).andExpect(status().isOk());

        verify(service).getEventByUserId(1L, 0, 10);
    }

    @SneakyThrows
    @Test
    void getEventByUserIdAndEventId() {
        when(service.getEventByUserIdAndEventId(1L, 1L)).thenReturn(EventMapper.toEventFull(event, 0L, 0L));

        mockMvc.perform(get("/users/{userId}/events/{eventId}", 1L, 1L)).andExpect(status().isOk());

        verify(service).getEventByUserIdAndEventId(1L, 1L);
    }

    @SneakyThrows
    @Test
    void patchEvent() {
        UpdateEventDto updateEventDto = UpdateEventDto.builder()
                .eventDate(LocalDateTime.now().withNano(0).plusDays(1))
                .description("aaaaaaaaaaaaaaaaaaaaaaaa")
                .title("aaaaaaaaa")
                .location(LocationDto.builder().lat("1124").lon("1421").build())
                .paid(false)
                .category(1L)
                .participantLimit(12)
                .requestModeration(false)
                .annotation("aaaaaaaaaaaaaaaaaaaaaaaaa")
                .stateAction(UpdateEventStatus.SEND_TO_REVIEW)
                .build();

        UpdateEvent updateEvent = EventMapper.toEventFromUpdateEvent(updateEventDto);
        when(service.patchEvent(1L, 1L, updateEvent)).thenReturn(EventMapper.toEventFull(event, 0L, 0L));

        String newEv = mockMvc.perform(patch("/users/{userId}/events/{eventId}", 1L, 1L, updateEventDto)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateEventDto))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andReturn()
                .getResponse()
                .getContentAsString();

        EventFullDto eventFullDto = EventMapper.toEventFullDto(EventMapper.toEventFull(event, 0L, 0L));

        assertEquals(newEv, objectMapper.writeValueAsString(eventFullDto));
    }

    @SneakyThrows
    @Test
    void getRequestByUserIdAndEventId() {
        Request request = Request.builder()
                .requester(User.builder().email("1412").name("asdas").id(1L).build())
                .event(event)
                .created(LocalDateTime.now().withNano(0))
                .id(1L)
                .build();
        when(service.getRequestByUserIdAndEventId(1L, 1L)).thenReturn(List.of(request));

        mockMvc.perform(get("/users//{userId}/events/{eventId}/requests", 1L, 1L)).andExpect(status().isOk());

        verify(service).getRequestByUserIdAndEventId(1L, 1L);
    }

    @SneakyThrows
    @Test
    void patchRequestByOwnerUser() {
        RequestShortDto requestShortDto = RequestShortDto.builder().requestIds(List.of(1L)).status(Status.CONFIRMED).build();
        RequestShort requestShort = RequestMapper.toRequestShort(requestShortDto);
        RequestShortUpdate requestShortUpdate =  new RequestShortUpdate();
        when(service.patchRequestByOwnerUser(1L, 1L, requestShort)).thenReturn(requestShortUpdate);

        String newEv = mockMvc.perform(patch("/users//{userId}/events/{eventId}/requests", 1L, 1L, requestShortDto)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestShortDto))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(newEv, objectMapper.writeValueAsString(RequestMapper.toRequestShortUpdateDto(requestShortUpdate)));
    }
}