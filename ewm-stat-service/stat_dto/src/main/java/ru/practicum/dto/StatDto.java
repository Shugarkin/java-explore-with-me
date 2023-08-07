package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class StatDto {

    @NotBlank(groups = Marker.Create.class)
    @Size(max = 50)
    private String app;

    @NotBlank(groups = Marker.Create.class)
    @Size(max = 50)
    private String uri;

    @NotBlank(groups = Marker.Create.class)
    @Size(max = 15)
    private String ip;

    @NotNull(groups = Marker.Create.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;
}
