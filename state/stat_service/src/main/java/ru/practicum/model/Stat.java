package ru.practicum.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Stat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long statId;

    @Column(name = "app")
    private String app;

    @Column(name = "ip")
    private String ip;

    @Column(name = "time_stamp")
    private String timestamp;

    @Column(name = "uri")
    private String uri;

    @Column(name = "hits")
    private long hits;

    @Column(name = "hits_unique")
    private long hitsUnique;
}
