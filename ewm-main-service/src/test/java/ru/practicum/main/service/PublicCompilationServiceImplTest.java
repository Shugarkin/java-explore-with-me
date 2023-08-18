//package ru.practicum.main.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.practicum.main.dao.CompilationMainServiceRepository;
//import ru.practicum.main.model.CompilationShort;
//import ru.practicum.main.model.Compilations;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class PublicCompilationServiceImplTest {
//
//    @Mock
//    private CompilationMainServiceRepository repository;
//
//    @Mock
//    private StatService statService;
//
//    @InjectMocks
//    private PublicCompilationServiceImpl service;
//
////    @Test
////    void getCompilationById() {
////        when(repository.findById(anyLong())).thenReturn(Optional.of(Compilations.builder().events(List.of()).build()));
////        when(statService.toView(List.of())).thenReturn(Map.of());
////        when(statService.toConfirmedRequest(List.of())).thenReturn(Map.of());
////        CompilationShort compilationById = service.getCompilationById(1L);
////        assertNotNull(compilationById);
////    }
////
////    @Test
////    void getCompilation() {
////        when(repository.findAllByPinned(any(), any())).thenReturn(List.of());
////
////        List<CompilationShort> compilation = service.getCompilation(false, 0, 10);
////
////        assertEquals(compilation, List.of());
////    }
//}