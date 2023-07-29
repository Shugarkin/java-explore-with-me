package ru.practicum.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
//@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Stat {

    //   @Id
    //   @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "view_id")
    private long viewId;

    //   @Column(name = "app")
    private String app;

    //  @Column(name = "uri")
    private String uri;

    //    @Column(name = "hits")
    private int hits;
}
