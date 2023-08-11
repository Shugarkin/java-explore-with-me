package ru.practicum.main.model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
public class Stat {

    private String app;

    private String uri;

    private long hits;

    public Stat(String app, String uri, long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}
