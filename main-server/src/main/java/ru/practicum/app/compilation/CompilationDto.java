package ru.practicum.app.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.app.event.EventShortDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    private Integer id;

    private String title;

    private Boolean pinned;

    private List<EventShortDto> events;
}
