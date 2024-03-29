package ru.practicum.app.service.compilation;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.app.dto.compilation.CompilationDto;
import ru.practicum.app.dto.compilation.NewCompilationDto;
import ru.practicum.app.repository.event.EventRepository;
import ru.practicum.app.mapper.compilation.CompilationMapper;
import ru.practicum.app.model.compilation.Compilation;
import ru.practicum.app.model.compilation.CompilationEvent;
import ru.practicum.app.repository.compilation.CompilationEventRepository;
import ru.practicum.app.repository.compilation.CompilationRepository;
import ru.practicum.app.service.event.EventServiceImpl;
import ru.practicum.app.dto.event.EventShortDto;
import ru.practicum.app.exception.CompilationNotFoundException;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationEventRepository compilationEventRepository;

    private final EventRepository eventRepository;
    private final EventServiceImpl eventService;

    @Autowired
    public CompilationServiceImpl(CompilationRepository compilationRepository,
                                  CompilationEventRepository compilationEventRepository,
                                  EventServiceImpl eventService,
                                  EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.compilationEventRepository = compilationEventRepository;
        this.eventService = eventService;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, @Min(0) int from, @Min(1) int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        log.info("компиляция получена");
        return compilationRepository.findAllByPinned(pinned, pageable)
                .stream()
                .map(i -> CompilationMapper.mapToCompilationDto(i, getCompilationEvents(i.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Integer compId) {
        Compilation compilation = findCompilationById(compId);
        log.info("компиляция получена по id");
        return CompilationMapper.mapToCompilationDto(compilation, getCompilationEvents(compId));
    }

    @Override
    public CompilationDto addCompilation(@Valid NewCompilationDto newCompilationDto) {

        Compilation compilation = CompilationMapper.mapToCompilation(newCompilationDto);
        Compilation addedCompilation = compilationRepository.save(compilation);
        Integer compId = addedCompilation.getId();

        for (Integer eventId : newCompilationDto.getEvents()) {
            CompilationEvent compilationEvent = new CompilationEvent(null, compId, eventId);
            compilationEventRepository.save(compilationEvent);
        }
        log.info("компиляция добавлена");
        return CompilationMapper.mapToCompilationDto(addedCompilation, getCompilationEvents(compId));
    }

    @Override
    public void deleteCompilationById(Integer compId) {
        if (compId == null) {
            throw new CompilationNotFoundException("подборка не найдена");
        }
        compilationRepository.deleteById(compId);
        log.info("компиляция удалена");
    }

    @Override
    public void addEventToCompilation(Integer compId, Integer eventId) {
        CompilationEvent compilationEvent = new CompilationEvent(null, compId, eventId);
        compilationEventRepository.save(compilationEvent);
        log.info("Эвент добавлен в компиляцию");
    }

    @Override
    public void deleteEventFromCompilation(Integer compId, Integer eventId) {
        compilationEventRepository.deleteByCompilationAndEvent(compId, eventId);
        log.info("Эвент удален из компиляции");
    }

    @Override
    public void pinCompilation(Integer compId) {
        Compilation compilation = findCompilationById(compId);
        compilation.setPinned(true);
        compilationRepository.save(compilation);
        log.info("Компиляцию закреплена");
    }

    @Override
    public void unpinCompilation(Integer compId) {
        Compilation compilation = findCompilationById(compId);
        compilation.setPinned(false);
        compilationRepository.save(compilation);
        log.info("Компиляцию откреплена");

    }

    @Override
    public Compilation findCompilationById(Integer compId) {
        Optional<Compilation> compilationOptional = compilationRepository.findById(compId);
        if (compilationOptional.isEmpty()) {
            String message = String.format("Compilation with id=%d was not found.", compId);
            throw new EntityNotFoundException(message);
        }
        log.info("Компиляцию найдена");
        return compilationOptional.get();
    }

    private List<EventShortDto> getCompilationEvents(Integer compId) {
        List<Integer> ids = compilationEventRepository.findCompilationEventIds(compId);
        log.info("Компиляцию получена");
        return eventService.getEventsByIds(ids);
    }
}
