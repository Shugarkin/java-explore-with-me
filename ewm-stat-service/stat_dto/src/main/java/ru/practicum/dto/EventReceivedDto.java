package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EventReceivedDto {

    @NotBlank(groups = {Marker.Create.class, Marker.Update.class})
    @Size(min = 20, max = 2000)
    private String annotation;

    @NotNull(groups = Marker.Create.class)
    @Min(1)
    private long category;

    @NotBlank(groups = {Marker.Create.class, Marker.Update.class})
    @Size(min = 20, max = 7000)
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    private LocationDto location;

    private boolean paid = false;

    private int participantLimit = 0;

    private boolean requestModeration = true;

    @NotBlank(groups = {Marker.Create.class, Marker.Update.class})
    @Size(min = 3, max = 120)
    private String title;

}
