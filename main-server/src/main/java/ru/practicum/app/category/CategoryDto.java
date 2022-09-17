package ru.practicum.app.category;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Integer id;

    @NotEmpty(message = "не может быть пустым")
    @Size(min = 1, max = 25, message = "должно быть менее 25 символов")
    private String name;
}
