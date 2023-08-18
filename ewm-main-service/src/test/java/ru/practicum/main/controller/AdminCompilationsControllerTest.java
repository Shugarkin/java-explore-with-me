//package ru.practicum.main.controller;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.practicum.main.dto.CompilationsDto;
//import ru.practicum.main.dto.NewCompilationDto;
//import ru.practicum.main.mapper.CompilationMapper;
//import ru.practicum.main.model.CompilationShort;
//import ru.practicum.main.model.Compilations;
//import ru.practicum.main.service.AdminCompilationsService;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class AdminCompilationsControllerTest {
//
//    @Mock
//    private AdminCompilationsService service;
//
//    @InjectMocks
//    private AdminCompilationsController controller;
//
//    private Compilations compilations = Compilations.builder()
//            .title("asd")
//            .pinned(true)
//            .build();
//
//    @Test
//    void postCompilations() {
//        NewCompilationDto newCompilationDto = NewCompilationDto.builder().events(List.of()).pinned(true).title("asd").build();
//        CompilationShort compilationShort = CompilationMapper.toCompilationShort(compilations, List.of());
//        when(service.createCompilation(any())).thenReturn(compilationShort);
//
//        CompilationsDto compilationsDto = controller.postCompilations(newCompilationDto);
//
//        assertEquals(compilationsDto.getEvents(), newCompilationDto.getEvents());
//        assertEquals(compilationsDto.getTitle(), newCompilationDto.getTitle());
//        assertEquals(compilationsDto.getPinned(), newCompilationDto.getPinned());
//    }
//
//    @Test
//    void deleteCompilation() {
//        controller.deleteCompilation(1);
//        verify(service).deleteCompilation(1);
//    }
//
//    @Test
//    void patchCompilation() {
//        NewCompilationDto newCompilationDto = NewCompilationDto.builder().events(List.of()).pinned(true).title("asd").build();
//        CompilationShort compilationShort = CompilationMapper.toCompilationShort(compilations, List.of());
//        when(service.patchCompilation(anyLong(), any())).thenReturn(compilationShort);
//
//        CompilationsDto compilationsDto = controller.patchCompilation(1, newCompilationDto);
//
//        assertEquals(compilationsDto.getEvents(), newCompilationDto.getEvents());
//        assertEquals(compilationsDto.getTitle(), newCompilationDto.getTitle());
//        assertEquals(compilationsDto.getPinned(), newCompilationDto.getPinned());
//    }
//}