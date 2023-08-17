package ru.practicum.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.main.mapper.CompilationMapper;
import ru.practicum.main.model.CompilationShort;
import ru.practicum.main.model.Compilations;
import ru.practicum.main.service.AdminCompilationsService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminCompilationsController.class)
class AdminCompilationsControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminCompilationsService service;

    private Compilations compilations = Compilations.builder()
            .title("asd")
            .pinned(true)
            .build();

    @SneakyThrows
    @Test
    void postCompilations() {
        NewCompilationDto newCompilationDto = NewCompilationDto.builder().events(List.of()).pinned(true).title("asd").build();
        CompilationShort compilationShort = CompilationMapper.toCompilationShort(compilations, List.of());

        when(service.createCompilation(any())).thenReturn(compilationShort);

        String newCom = mockMvc.perform(post("/admin/compilations", newCompilationDto)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(compilationShort))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(newCom, objectMapper.writeValueAsString(compilationShort));
    }

    @Test
    void deleteCompilation() {
        service.deleteCompilation(1);
        verify(service).deleteCompilation(1);

    }

    @SneakyThrows
    @Test
    void patchCompilation() {
        NewCompilationDto newCompilationDto = NewCompilationDto.builder().events(List.of()).pinned(true).title("asd").build();
        CompilationShort compilationShort = CompilationMapper.toCompilationShort(compilations, List.of());

        when(service.patchCompilation(anyLong(), any())).thenReturn(compilationShort);

        String newCom = mockMvc.perform(patch("/admin/compilations/{compId}", 1, newCompilationDto)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(compilationShort))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(newCom, objectMapper.writeValueAsString(compilationShort));
    }
}