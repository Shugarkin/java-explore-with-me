package ru.practicum.main.model;


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
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lon")
    private String lon;
}
