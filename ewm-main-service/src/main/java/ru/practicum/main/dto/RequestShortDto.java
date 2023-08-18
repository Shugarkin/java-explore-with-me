package ru.practicum.main.dto;

import lombok.*;
import ru.practicum.dto.Status;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RequestShortDto {

    private List<Long> requestIds;

    private Status status;
}
