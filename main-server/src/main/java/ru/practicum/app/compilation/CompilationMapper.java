package ru.practicum.app.compilation;

import org.springframework.stereotype.Component;
import ru.practicum.app.event.EventShortDto;

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
