package ru.practicum.app.dto.compilation;

import lombok.*;
import ru.practicum.app.dto.event.EventShortDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    private Integer id;

    private String title;

    private Boolean pinned;

    private List<EventShortDto> events;
}
