package ru.practicum.main.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.practicum.client.StatClient;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatUniqueOrNotDto;
import ru.practicum.main.dao.RequestMainServiceRepository;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatServiceImplTest {

    @Mock
    private RequestMainServiceRepository requestMainServiceRepository;

    @Mock
    private StatClient statClient;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private StatServiceImpl service;

    @Test
    void toConfirmedRequest() {
        when(requestMainServiceRepository.countByEventId(any())).thenReturn(List.of());

        Map<Long, Long> confirmedRequest = service.toConfirmedRequest(List.of());

        assertEquals(confirmedRequest, Map.of());
    }

    private StatUniqueOrNotDto[] stat = new StatUniqueOrNotDto[1];

    @SneakyThrows
    @Test
    void toView() {
        stat[0] = StatUniqueOrNotDto.builder().hits(0L).uri("afqwfq1").app("sadsaf2").build();
        when(statClient.getStatEvent(any(), any(), any(), anyBoolean())).thenReturn(ResponseEntity.accepted().body(List.of()));
        when(objectMapper.writeValueAsString(any())).thenReturn("asd");
        when(objectMapper.readValue("asd", StatUniqueOrNotDto[].class)).thenReturn(stat);
        Map<Long, Long> view = service.toView(List.of());

        assertNotNull(view);
    }

    @Test
    void addHits() {
        service.addHits(StatDto.builder().build());
        verify(statClient).postStatEvent(StatDto.builder().build());
    }
}