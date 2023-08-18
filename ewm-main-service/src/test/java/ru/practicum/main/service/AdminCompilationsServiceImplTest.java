package ru.practicum.main.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.main.dto.State;
import ru.practicum.main.dao.CompilationMainServiceRepository;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminCompilationsServiceImplTest {

    @InjectMocks
    private AdminCompilationsServiceImpl service;

    @Mock
    private CompilationMainServiceRepository repository;

    @Mock
    private EventMainServiceRepository eventMainServiceRepository;

    @Mock
    private StatService statService;

//    private Event event = Event.builder()
//            .id(1L)
//            .eventDate(LocalDateTime.now().withNano(0).plusDays(1))
//            .description("aaaaaaaaaaaaaaaaaaaaaaaa")
//            .title("aaaaaaaaa")
//            .location(Location.builder().lat("1124").lon("1421").build())
//            .paid(false)
//            .category(Categories.builder().id(1L).name("asdsa").build())
//            .participantLimit(12)
//            .createdOn(LocalDateTime.now().withNano(0))
//            .requestModeration(false)
//            .annotation("aaaaaaaaaaaaaaaaaaaaaaaaa")
//            .initiator(User.builder().id(1L).name("asdasf").email("asdsf@asfs.ru").build())
//            .state(State.PUBLISHED)
//            .publishedOn(LocalDateTime.now().withNano(0))
//            .build();
//
//    @Test
//    void createCompilation() {
//        when(eventMainServiceRepository.findAllById(any())).thenReturn(List.of(event));
//        when(repository.save(any())).thenReturn(Compilations.builder().events(List.of()).build());
//        when(statService.toView(anyList())).thenReturn(Map.of());
//        when(statService.toConfirmedRequest(anyList())).thenReturn(Map.of());
//
//        CompilationShort compilation = service.createCompilation(NewCompilation.builder().build());
//        assertNotNull(compilation);
//    }
//
//    @Test
//    void deleteCompilation() {
//        when(repository.existsById(1L)).thenReturn(true);
//        service.deleteCompilation(1L);
//        verify(repository).deleteById(1L);
//    }
//
//    @Test
//    void patchCompilation() {
//        when(repository.findById(anyLong())).thenReturn(Optional.of(Compilations.builder().events(List.of()).build()));
//        when(statService.toView(anyList())).thenReturn(Map.of());
//        when(statService.toConfirmedRequest(anyList())).thenReturn(Map.of());
//
//        CompilationShort compilationShort = service.patchCompilation(1L, NewCompilation.builder().build());
//
//        assertNotNull(compilationShort);
//    }
}