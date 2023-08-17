package ru.practicum.main.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.State;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminEventServiceImplTest {

    @Mock
    private EventMainServiceRepository repository;

    @Mock
    private StatService statService;

    @InjectMocks
    private AdminEventServiceImpl service;

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

    @Test
    void patchAdminEvent() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(event));
        when(statService.toView(anyList())).thenReturn(Map.of());
        when(statService.toConfirmedRequest(anyList())).thenReturn(Map.of());

        EventFull eventFull = service.patchAdminEvent(1L, AdminEvent.builder().build());
        assertNotNull(eventFull);
    }

    @Test
    void getAdminEvents() {
        when(repository.findAllByParam(anyList(), anyList(), anyList(), any(), any(), any())).thenReturn(List.of(event));
        when(statService.toView(anyList())).thenReturn(Map.of());
        when(statService.toConfirmedRequest(anyList())).thenReturn(Map.of());

        List<EventFull> adminEvents = service.getAdminEvents(List.of(), List.of(), List.of(), null, null, 0, 10);

        assertEquals(adminEvents.size(), 1);
    }
}