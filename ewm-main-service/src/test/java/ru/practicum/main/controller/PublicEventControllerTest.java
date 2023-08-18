package ru.practicum.main.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.State;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.Categories;
import ru.practicum.main.model.Location;
import ru.practicum.main.model.User;
import ru.practicum.main.service.PublicEventService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicEventControllerTest {

    @Mock
    private PublicEventService service;

    @InjectMocks
    private PublicEventController controller;


    @Test
    void getPublicEvents() {
        when(service.getPublicEvents(any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(List.of());

        List<EventShortDto> publicEvents = controller
                .getPublicEvents(null, null, null, null, null, null, null, 0, 10, null);

        assertEquals(publicEvents, List.of());
    }

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
//
//        when(service.getPublicEvent(anyLong(), any())).thenReturn(event);
//
//        EventFullDto publicEvent = controller.getPublicEvent(anyLong(), any());
//
//        assertEquals(publicEvent, EventMapper.toEventFullDto(event));
//    }
}