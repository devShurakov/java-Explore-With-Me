package ru.practicum.app.dto.compilation;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;

    private Boolean pinned;

    private List<Integer> events;

}
