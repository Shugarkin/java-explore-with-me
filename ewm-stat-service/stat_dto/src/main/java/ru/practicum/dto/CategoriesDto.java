package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesDto {

    private long id;

    @NotBlank(groups = {Marker.Create.class, Marker.Update.class})
    @Size(min = 1, max = 50)
    private String name;
}
