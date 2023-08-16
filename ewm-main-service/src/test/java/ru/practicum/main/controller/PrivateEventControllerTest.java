package ru.practicum.main.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.*;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.*;
import ru.practicum.main.service.AdminEventService;
import ru.practicum.main.service.PrivateEventService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrivateEventControllerTest {

    @Mock
    private PrivateEventService service;

    @InjectMocks
    private PrivateEventController controller;

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

    private EventFull eventFull = EventMapper.toEventFull(event, 0L, 0L);

    @Test
    void createEvent() {
        when(service.createEvent(anyLong(), any())).thenReturn(event);

        EventFullDto event1 = controller.createEvent(1L, eventReceivedDto);

        EventFullDto eventFullDto = EventMapper.toEventFullDto(eventFull);
        assertEquals(event1, eventFullDto);
    }

    @Test
    void getEventByUserId() {
        when(service.getEventByUserId(1L, 0, 10)).thenReturn(List.of(eventFull));

        List<EventFullDto> eventByUserId = controller.getEventByUserId(1L, 0, 10);

        assertEquals(eventByUserId, List.of(EventMapper.toEventFullDto(eventFull)));
    }

    @Test
    void getEventByUserIdAndEventId() {
        when(service.getEventByUserIdAndEventId(1L, 1L)).thenReturn(eventFull);

        EventFullDto eventByUserIdAndEventId = controller.getEventByUserIdAndEventId(1L, 1L);

        assertEquals(EventMapper.toEventFullDto(eventFull), eventByUserIdAndEventId);

    }

    @Test
    void patchEvent() {
        UpdateEventDto updateEventDto = UpdateEventDto.builder()
                .stateAction(UpdateEventStatus.SEND_TO_REVIEW)
                .eventDate(LocalDateTime.now().withNano(0))
                .participantLimit(0)
                .annotation("12432412421412")
                .title("124124")
                .category(1L)
                .description("142521521")
                .build();

        when(service.patchEvent(anyLong(), anyLong(), any())).thenReturn(eventFull);

        EventFullDto eventFullDto = controller.patchEvent(1L, 1L, updateEventDto);

        assertEquals(EventMapper.toEventFullDto(eventFull), eventFullDto);
    }

    @Test
    void getRequestByUserIdAndEventId() {
        when(service.getRequestByUserIdAndEventId(1L, 1L)).thenReturn(List.of());

        List<RequestDto> requestByUserIdAndEventId = controller.getRequestByUserIdAndEventId(1L, 1L);

        assertEquals(requestByUserIdAndEventId, List.of());
    }

    @Test
    void patchRequestByOwnerUser() {
        when(service.patchRequestByOwnerUser(anyLong(), anyLong(), any())).thenReturn(new RequestShortUpdate());

        RequestShortUpdateDto requestShortUpdateDto = controller.patchRequestByOwnerUser(1L, 1L, new RequestShortDto());

        assertEquals(requestShortUpdateDto, new RequestShortUpdateDto());
    }
}