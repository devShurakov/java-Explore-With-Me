package ru.practicum.app.compilation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.app.event.EventServiceImpl;
import ru.practicum.app.event.EventShortDto;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CompilationServiceImpl {
    private final CompilationRepository compilationRepository;
    private final CompilationEventRepository compilationEventRepository;
    private final EventServiceImpl eventService;

    private final CompilationMapper compilationMapper;

    @Autowired
    public CompilationServiceImpl(CompilationRepository compilationRepository,
                                  CompilationEventRepository compilationEventRepository,
                                  EventServiceImpl eventService, CompilationMapper compilationMapper) {
        this.compilationRepository = compilationRepository;
        this.compilationEventRepository = compilationEventRepository;
        this.eventService = eventService;
        this.compilationMapper = compilationMapper;
    }

    public List<CompilationDto> getCompilations(Boolean pinned, @Min(0) int from, @Min(1) int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return compilationRepository.findAllByPinned(pinned, pageable)
                .stream()
                .map(i -> CompilationMapper.mapToCompilationDto(i, getCompilationEvents(i.getId())))
                .collect(Collectors.toList());
    }

    public CompilationDto getCompilationById(Integer compId) {
        Compilation compilation = findCompilationById(compId);
        return CompilationMapper.mapToCompilationDto(compilation, getCompilationEvents(compId));
    }

    public CompilationDto addCompilation(@Valid NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationMapper.mapToCompilation(newCompilationDto);
        Compilation addedCompilation = compilationRepository.save(compilation);
        Integer compId = addedCompilation.getId();

        for (Integer eventId : newCompilationDto.getEvents()) {
            CompilationEvent compilationEvent = new CompilationEvent(null, compId, eventId, null, null);
            compilationEventRepository.save(compilationEvent);
        }
        return CompilationMapper.mapToCompilationDto(addedCompilation, getCompilationEvents(compId));
    }

    public void deleteCompilationById(Integer compId) {
        compilationRepository.deleteById(compId);
    }

    public void addEventToCompilation(Integer compId, Integer eventId) {
        CompilationEvent compilationEvent = new CompilationEvent(null, compId, eventId, null, null);
        compilationEventRepository.save(compilationEvent);
    }

    public void deleteEventFromCompilation(Integer compId, Integer eventId) {
        compilationEventRepository.deleteByCompilationAndEvent(compId, eventId);
    }

    public void pinCompilation(Integer compId) {
        Compilation compilation = findCompilationById(compId);
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }

    public void unpinCompilation(Integer compId) {
        Compilation compilation = findCompilationById(compId);
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    private Compilation findCompilationById(Integer compId) {
        Optional<Compilation> compilationOptional = compilationRepository.findById(compId);
        if (compilationOptional.isEmpty()) {
            String message = String.format("Compilation with id=%d was not found.", compId);
            throw new EntityNotFoundException(message);
        }
        return compilationOptional.get();
    }

    private List<EventShortDto> getCompilationEvents(Integer compId) {
        List<Integer> ids = compilationEventRepository.findCompilationEventIds(compId);
        return eventService.getEventsByIds(ids);
    }
}
