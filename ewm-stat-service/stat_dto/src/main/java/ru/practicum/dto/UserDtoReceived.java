package ru.practicum.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoReceived {

    private long id;

    @Email(groups = {Marker.Create.class})
    @Size(min = 6, max = 64, groups = {Marker.Create.class})
    @NotBlank(groups = {Marker.Create.class})
    private String email;

    @NotBlank(groups = {Marker.Create.class})
    @Size(min = 2,max = 250, groups = {Marker.Create.class})
    private String name;
}
