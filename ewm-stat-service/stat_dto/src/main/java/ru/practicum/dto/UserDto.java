package ru.practicum.dto;

import lombok.*;


@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;

    private String name;
}
