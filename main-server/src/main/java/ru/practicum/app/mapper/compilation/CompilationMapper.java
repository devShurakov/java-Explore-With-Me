package ru.practicum.app.mapper.compilation;

import org.springframework.stereotype.Component;
import ru.practicum.app.model.compilation.Compilation;
import ru.practicum.app.dto.compilation.CompilationDto;
import ru.practicum.app.dto.compilation.NewCompilationDto;
import ru.practicum.app.dto.event.EventShortDto;

import java.util.List;

@Component
public class CompilationMapper {
    public static Compilation mapToCompilation(NewCompilationDto newCompilationDto) {
        return new Compilation(
                null,
                newCompilationDto.getTitle(),
                newCompilationDto.getPinned()
        );
    }

    public static CompilationDto mapToCompilationDto(Compilation compilation,
                                                     List<EventShortDto> compilationEvents) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                compilationEvents
        );
    }

}
