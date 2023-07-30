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
    @Column(name = "stat_id")
    private long statId;

    @ManyToOne
    @JoinColumn(name = "event")
    private StatEvent event;

    @Column(name = "hits")
    private long hits;

    @Column(name = "hits_unique")
    private long hitsUnique;
}
