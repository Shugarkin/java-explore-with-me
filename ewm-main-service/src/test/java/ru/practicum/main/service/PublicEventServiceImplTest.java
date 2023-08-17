package ru.practicum.main.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.State;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.model.Event;
import ru.practicum.main.model.EventFull;
import ru.practicum.main.model.EventShort;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicEventServiceImplTest {

    @Mock
    private EventMainServiceRepository repository;

    @Mock
    private StatService statService;

    @InjectMocks
    private PublicEventServiceImpl service;

    @Mock
    private HttpServletRequest request;
    @Test
    void getPublicEvents() {
        when(repository.findAllEvents(anyString(), anyList(), anyBoolean(), any(), any(), anyBoolean(), anyString(), any())).thenReturn(List.of());
        when(statService.toView(List.of())).thenReturn(Map.of());
        when(statService.toConfirmedRequest(List.of())).thenReturn(Map.of());
        when(request.getRequestURI()).thenReturn("asfdasf");
        when(request.getRemoteAddr()).thenReturn("afsasf");
        List<EventShort> publicEvents = service.getPublicEvents("", List.of(), false, null,
                null, false, "id", 0, 10, request);
        assertEquals(publicEvents, List.of());
    }

    @Test
    void getPublicEvent() {
        when(repository.findById(any())).thenReturn(Optional.of(Event.builder()
                .id(1L).paid(false).requestModeration(false).participantLimit(1).state(State.PUBLISHED).build()));
        when(statService.toView(anyList())).thenReturn(Map.of());
        when(statService.toConfirmedRequest(anyList())).thenReturn(Map.of());
        when(request.getRequestURI()).thenReturn("asfdasf");
        when(request.getRemoteAddr()).thenReturn("afsasf");

        EventFull publicEvent = service.getPublicEvent(1L, request);

        assertNotNull(publicEvent);
    }
}