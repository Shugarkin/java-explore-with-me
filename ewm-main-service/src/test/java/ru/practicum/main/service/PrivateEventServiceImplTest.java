package ru.practicum.main.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.State;
import ru.practicum.main.dao.*;
import ru.practicum.main.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrivateEventServiceImplTest {

    @Mock
    private EventMainServiceRepository repository;

    @Mock
    private CategoriesMainServiceRepository categoriesMainServiceRepository;

    @Mock
    private UserMainServiceRepository userMainServiceRepository;

    @Mock
    private RequestMainServiceRepository requestMainServiceRepository;

    @Mock
    private LocationMainServiceRepository locationMainServiceRepository;

    @Mock
    private StatService statService;

    @InjectMocks
    private PrivateEventServiceImpl service;

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
            .state(State.PENDING)
            .publishedOn(LocalDateTime.now().withNano(0))
            .build();

    @Test
    void createEvent() {
        when(categoriesMainServiceRepository.findById(anyLong())).thenReturn(Optional.of(Categories.builder().build()));
        when(locationMainServiceRepository.save(any())).thenReturn(Location.builder().build());
        when(userMainServiceRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().build()));
        service.createEvent(1L, event);

        verify(repository).save(event);
    }

//    @Test
//    void getEventByUserId() {
//        when(userMainServiceRepository.existsById(anyLong())).thenReturn(true);
//        when(repository.findAllByInitiatorId(anyLong(), any())).thenReturn(List.of(event));
//        when(statService.toView(anyList())).thenReturn(Map.of());
//        when(statService.toConfirmedRequest(anyList())).thenReturn(Map.of());
//
//        List<EventFull> eventByUserId = service.getEventByUserId(1L, 0, 10);
//
//        assertEquals(eventByUserId.size(), 1);
//    }
//
//    @Test
//    void getEventByUserIdAndEventId() {
//        when(repository.findByIdAndInitiatorId(anyLong(), anyLong())).thenReturn(Optional.of(event));
//        when(statService.toView(anyList())).thenReturn(Map.of());
//        when(statService.toConfirmedRequest(anyList())).thenReturn(Map.of());
//
//        EventFull eventByUserIdAndEventId = service.getEventByUserIdAndEventId(1L, 1L);
//        assertNotNull(eventByUserIdAndEventId);
//    }
//
//    @Test
//    void patchEvent() {
//        when(repository.findByIdAndInitiatorId(anyLong(), anyLong())).thenReturn(Optional.of(event));
//        when(statService.toView(anyList())).thenReturn(Map.of());
//        when(statService.toConfirmedRequest(anyList())).thenReturn(Map.of());
//
//        EventFull eventFull = service.patchEvent(1L, 1L, UpdateEvent.builder().build());
//
//        assertNotNull(eventFull);
//    }
//
//    @Test
//    void getRequestByUserIdAndEventId() {
//        when(repository.existsByIdAndInitiatorId(anyLong(), anyLong())).thenReturn(true);
//        when(requestMainServiceRepository.findAllByEventId(anyLong())).thenReturn(List.of());
//
//        List<Request> requestByUserIdAndEventId = service.getRequestByUserIdAndEventId(1L, 1L);
//
//        assertEquals(requestByUserIdAndEventId, List.of());
//    }
//
//    @Test
//    void patchRequestByOwnerUser() {
//        when(userMainServiceRepository.existsById(anyLong())).thenReturn(true);
//        when(repository.findById(anyLong())).thenReturn(Optional.of(event));
//        when(statService.toConfirmedRequest(anyList())).thenReturn(Map.of());
//
//        RequestShortUpdate requestShortUpdate = service.patchRequestByOwnerUser(1L, 1L, RequestShort.builder().requestIds(List.of()).build());
//
//        assertNotNull(requestShortUpdate);
//    }
}