package ru.practicum.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.StatDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class StatClient  extends BaseClient {


    public StatClient(@Value("${ewm-stats-service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> postStatEvent(StatDto stat) {
        return post("/hit", stat);
    }

    public ResponseEntity<Object> getStatEvent(String start, String end, @Nullable List<String> uris, boolean unique) {
        Map<String, Object> parameters;
        if (uris == null) {
            parameters = Map.of("start", encodeValue(start),
                    "end", encodeValue(end),
                    "unique", unique);
            return get("/stats?start={start}&end={end}&unique={unique}", parameters);
        } else {
            parameters = Map.of("start", encodeValue(start),
                    "end", encodeValue(end),
                    "uris", uris,
                    "unique", unique);
            return get("/stats?start={start}&end={end}&unique={unique}&uris={uris}", parameters);
        }
    }

    private String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
