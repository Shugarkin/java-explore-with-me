package ru.practicum.model;


import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
public class StatUniqueOrNot {

    private String app;

    private String uri;

    private long hits;

    public StatUniqueOrNot(String app, String uri, long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}
