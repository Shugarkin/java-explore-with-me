package controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatClient;
import ru.practicum.dto.StatDto;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StateController {

    private final StatClient client;

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam String start,
                                           @RequestParam String end,
                                           @RequestParam(required = false) List<String> uris,
                                           @RequestParam boolean unique) {
        return client.getStatEvent(start, end, uris, unique);
    }

    @PostMapping("/hit")
    public ResponseEntity<Object> postHit(@RequestParam String app,
                                          @RequestParam String uri,
                                          @RequestParam String ip,
                                          @RequestParam String timestamp) {
        StatDto statDto = StatDto.builder().app(app).uri(uri).ip(ip).timestamp(timestamp).build();
        return client.postStatEvent(statDto);
    }
}
