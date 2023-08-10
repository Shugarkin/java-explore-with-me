package ru.practicum.model;

import lombok.*;
import ru.practicum.dto.State;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "annotation")
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;

    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @Column(name = "description")
    private String description;

    @Column(name = "eventDate")
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "participant_Limit")
    private int participantLimit;

    @Column(name = "request_Moderation")
    private boolean requestModeration;

    @Column(name = "state_event")
    private State state;

    @Column(name = "title")
    private String title;

}
