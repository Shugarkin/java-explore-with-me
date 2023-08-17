package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventDto {

    @Size(min = 20, max = 2000, groups = Marker.Update.class)
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000, groups = Marker.Update.class)
    private String description;

    @Future(groups = Marker.Update.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    @Size(min = 3, max = 120, groups = Marker.Update.class)
    private String title;

    private UpdateEventStatus stateAction;
}
