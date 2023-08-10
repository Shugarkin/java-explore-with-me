package ru.practicum.dto;

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

    private LocalDateTime eventDate;

    private LocationDto location;

//    @NotNull(groups = {Marker.Create.class, Marker.Update.class})
    private boolean paid;

//    @NotNull(groups = {Marker.Create.class, Marker.Update.class})
    private int participantLimit;

//    @NotNull(groups = {Marker.Create.class, Marker.Update.class})
    private boolean requestModeration;

    @NotBlank(groups = {Marker.Create.class, Marker.Update.class})
    @Size(min = 3, max = 120)
    private String title;

}
