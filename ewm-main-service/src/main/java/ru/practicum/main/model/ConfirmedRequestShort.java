package ru.practicum.main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConfirmedRequestShort {

    private Long eventId;

    private Long confirmedRequestsCount;

    public ConfirmedRequestShort(Long eventId, Long confirmedRequestsCount) {
        this.eventId = eventId;
        this.confirmedRequestsCount = confirmedRequestsCount;
    }
}
