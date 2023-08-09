package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesDto {

    private long id;

    @NotBlank(groups = {Marker.Create.class, Marker.Update.class})
    private String name;
}
