package ru.practicum.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.dto.Marker;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EventReceivedDto {

    @NotBlank(groups = {Marker.Create.class, Marker.Update.class})
    @Size(min = 20, max = 2000, groups = {Marker.Create.class, Marker.Update.class})
    private String annotation;

    @NotNull(groups = Marker.Create.class)
    private Long category;

    @NotBlank(groups = {Marker.Create.class, Marker.Update.class})
    @Size(min = 20, max = 7000, groups = {Marker.Create.class, Marker.Update.class})
    private String description;

    @Future(groups = {Marker.Create.class, Marker.Update.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid = false;

    private Integer participantLimit = 0;

    private Boolean requestModeration = true;

    @NotBlank(groups = {Marker.Create.class, Marker.Update.class})
    @Size(min = 3, max = 120, groups = {Marker.Create.class, Marker.Update.class})
    private String title;

}
