package ru.practicum.app.dto.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
public class NewCompilationDto {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;

    private Boolean pinned;

    private List<Integer> events;

}
