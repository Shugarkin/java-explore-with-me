//package ru.practicum.main.controller;
//
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.practicum.main.model.CompilationShort;
//import ru.practicum.main.service.PublicCompilationService;
//
//import java.util.List;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(PublicCompilationsController.class)
//class PublicCompilationsControllerITTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PublicCompilationService service;
//
//    @SneakyThrows
//    @Test
//    void getCompilationById() {
//        when(service.getCompilationById(1L)).thenReturn(CompilationShort.builder().events(List.of()).id(1L).pinned(false).title("asasfasf").build());
//
//        mockMvc.perform(get("/compilations/{compId}", 1)).andExpect(status().isOk());
//
//        verify(service).getCompilationById(1L);
//    }
//
//    @SneakyThrows
//    @Test
//    void getCompilation() {
//        when(service.getCompilation(null, 0, 10)).thenReturn(List.of());
//
//        mockMvc.perform(get("/compilations")).andExpect(status().isOk());
//
//        verify(service).getCompilation(null, 0, 10);
//    }
//}