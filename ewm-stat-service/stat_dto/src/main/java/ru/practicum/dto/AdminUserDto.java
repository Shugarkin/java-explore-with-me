package ru.practicum.dto;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserDto {
    private long id;

    private String name;

    private String email;
}
