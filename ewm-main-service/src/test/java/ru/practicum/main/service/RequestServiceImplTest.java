//package ru.practicum.main.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.practicum.main.dto.State;
//import ru.practicum.main.dao.EventMainServiceRepository;
//import ru.practicum.main.dao.RequestMainServiceRepository;
//import ru.practicum.main.dao.UserMainServiceRepository;
//import ru.practicum.main.model.ConfirmedRequestShort;
//import ru.practicum.main.model.Event;
//import ru.practicum.main.model.Request;
//import ru.practicum.main.model.User;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class RequestServiceImplTest {
//
//    @Mock
//    private RequestMainServiceRepository repository;
//
//    @Mock
//    private UserMainServiceRepository userMainServiceRepository;
//
//    @Mock
//    private EventMainServiceRepository eventMainServiceRepository;
//
//
//    @InjectMocks
//    private RequestServiceImpl service;
//
//
//    @Test
//    void createRequest() {
//        when(userMainServiceRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().id(1L).build()));
//        when(eventMainServiceRepository.findById(anyLong())).thenReturn(
//                Optional.of(Event.builder().participantLimit(0).requestModeration(false).state(State.PUBLISHED).initiator(User.builder().id(2L).build()).build()));
//        when(repository.existsByRequesterIdAndEventId(anyLong(), anyLong())).thenReturn(false);
//        when(repository.countByEventId(any())).thenReturn(List.of(new ConfirmedRequestShort()));
//        when(repository.save(any())).thenReturn(Request.builder().build());
//
//        Request request = service.createRequest(1L, 1L);
//
//        assertNotNull(request);
//
//    }
//
//    @Test
//    void getRequests() {
//        when(repository.findAllByRequesterId(anyLong())).thenReturn(List.of());
//
//        List<Request> requests = service.getRequests(1L);
//        assertEquals(requests, List.of());
//    }
//
//    @Test
//    void canselRequest() {
//        when(userMainServiceRepository.existsById(anyLong())).thenReturn(true);
//        when(repository.findById(anyLong())).thenReturn(Optional.of(Request.builder().build()));
//
//        Request request = service.canselRequest(1L, 1L);
//
//        assertNotNull(request);
//    }
//}