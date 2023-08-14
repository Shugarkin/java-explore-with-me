package ru.practicum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.StatDto;
import ru.practicum.mapper.StatMapper;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;
import ru.practicum.service.StatService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StatServiceController.class)
class StatServiceControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StatService statService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Stat stat = Stat.builder()
            .statId(1)
            .ip("85.249.18.233")
            .uri("/events")
            .timestamp(LocalDateTime.parse("2035-05-05 00:00:00", formatter))
            .app("ewm-main-service")
            .build();

    @SneakyThrows
    @Test
    void postStatEvent() {

        StatDto statDto = StatMapper.toStatDto(stat);

        when(statService.postStat(any())).thenReturn(stat);

        String newStat = mockMvc.perform(post("/hit", statDto)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(statDto))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(newStat, objectMapper.writeValueAsString(statDto));
    }


    @SneakyThrows
    @Test
    void getStatEvent() {
        String start = "2020-05-05 00:00:00";
        String end = "2035-05-05 00:00:00";

        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);
        List<String> uris = List.of();
        boolean unique = false;

        List<StatUniqueOrNot> listUnique = List.of(StatUniqueOrNot.builder().hits(1).app("ewm-main-service").uri("/events").build());

        when(statService.getStat(startDate, endDate, uris, unique)).thenReturn(listUnique);

        String newUniqueList = mockMvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", end)
                        .param("uris", "")
                        .param("unique", String.valueOf(unique)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(newUniqueList, objectMapper.writeValueAsString(listUnique)); //проверка на получение уникальных записей без учеба uri

        verify(statService).getStat(startDate, endDate, uris, unique);
    }
}