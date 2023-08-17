package ru.practicum.main.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.CompilationsDto;
import ru.practicum.main.model.CompilationShort;
import ru.practicum.main.service.PublicCompilationService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicCompilationsControllerTest {

    @Mock
    private PublicCompilationService service;

    @InjectMocks
    private PublicCompilationsController controller;

    private CompilationShort compilations = CompilationShort.builder().events(List.of()).id(1L).pinned(false).title("asasfasf").build();

    @Test
    void getCompilationById() {
        when(service.getCompilationById(1L)).thenReturn(compilations);

        CompilationsDto compilationById = controller.getCompilationById(1L);

        assertNotNull(compilationById);
    }

    @Test
    void getCompilation() {
        when(service.getCompilation(null, 0,10)).thenReturn(List.of());

        List<CompilationsDto> compilation = controller.getCompilation(null, 0, 10);

        assertNotNull(compilation);
    }
}