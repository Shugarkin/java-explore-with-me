package ru.practicum.main.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.RequestDto;
import ru.practicum.dto.State;
import ru.practicum.dto.Status;
import ru.practicum.main.mapper.RequestMapper;
import ru.practicum.main.model.*;
import ru.practicum.main.service.RequestService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestControllerTest {

    @Mock
    private RequestService service;

    @InjectMocks
    private RequestController controller;


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

//    @Test
//    void createRequest() {
//        when(service.createRequest(anyLong(), any())).thenReturn(request);
//
//        RequestDto requestNew = controller.createRequest(anyLong(), any());
//
//        assertEquals(requestNew, RequestMapper.toRequestDto(request));
//    }
//
//    @Test
//    void getRequests() {
//        when(service.getRequests(anyLong())).thenReturn(List.of());
//
//        List<RequestDto> requests = controller.getRequests(anyLong());
//
//        assertEquals(requests, List.of());
//    }
//
//    @Test
//    void canselRequest() {
//        when(service.canselRequest(anyLong(), anyLong())).thenReturn(request);
//
//        RequestDto requestDto = controller.canselRequest(anyLong(), anyLong());
//
//        assertEquals(requestDto, RequestMapper.toRequestDto(request));
//    }
}