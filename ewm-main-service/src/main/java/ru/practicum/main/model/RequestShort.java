package ru.practicum.main.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.dto.Status;

import java.util.List;


@Getter
@Setter
@Builder
@EqualsAndHashCode
public class RequestShort {
    private List<Long> requestIds;

    private Status status;
}
