package ru.practicum.app.compilation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.app.event.EventRepository;
import ru.practicum.app.event.EventServiceImpl;
import ru.practicum.app.event.EventShortDto;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CompilationServiceImpl {
    private final CompilationRepository compilationRepository;
    private final CompilationEventRepository compilationEventRepository;

    private final EventRepository eventRepository;
    private final EventServiceImpl eventService;

    private final CompilationMapper compilationMapper;

    @Autowired
    public CompilationServiceImpl(CompilationRepository compilationRepository,
                                  CompilationEventRepository compilationEventRepository,
                                  EventServiceImpl eventService, CompilationMapper compilationMapper,
                                  EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.compilationEventRepository = compilationEventRepository;
        this.eventService = eventService;
        this.compilationMapper = compilationMapper;
        this.eventRepository = eventRepository;
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

//    public class NewCompilationDto {
//        private String title;
//        private Boolean pinned;
//        private List<Integer> events;

//    @Entity
//    @Table(name = "compilation_event")
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public class CompilationEvent {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Integer id;
//        @Column(name = "compilation_id")
//        private Integer compilation;
//        @Column(name = "event_id")
//        private Integer event;
//        @Column(name = "title", nullable = false)
//        String title;
//        @Column(name = "pinned", nullable = false)
//        Boolean pinned;


    public CompilationDto addCompilation(@Valid NewCompilationDto newCompilationDto) {
//
//        List<Event> events = new ArrayList<>();
//
//        for (Integer eventId : newCompilationDto.getEvents()) {
//            events.add(eventRepository.findById(eventId).get());
//        }
//
//        Compilation compilation = new Compilation();
//        compilation.setTitle(newCompilationDto.getTitle());
//        compilation.setPinned(newCompilationDto.getPinned());
//        compilationRepository.save(compilation);
//
//        for (Event eventId : events) {
//            CompilationEvent compilationEvent = new CompilationEvent(null, compId, eventId, null, null);
//            compilationEventRepository.save(compilationEvent);
//        }
//
//
//        return CompilationMapper.toCompilationDto(compilation);


        Compilation compilation = compilationMapper.mapToCompilation(newCompilationDto);
        Compilation addedCompilation = compilationRepository.save(compilation);
        Integer compId = addedCompilation.getId();

        for (Integer eventId : newCompilationDto.getEvents()) {
            CompilationEvent compilationEvent = new CompilationEvent(null, compId, eventId);
            compilationEventRepository.save(compilationEvent);
        }
        return CompilationMapper.mapToCompilationDto(addedCompilation, getCompilationEvents(compId));
    }

    public void deleteCompilationById(Integer compId) {
        if (compId == null) {
            throw new CompilationNotFoundException("подборка не найдена");
        }
        compilationRepository.deleteById(compId);
//        compilationEventRepository.deleteById(compId);

    }

    public void addEventToCompilation(Integer compId, Integer eventId) {
        CompilationEvent compilationEvent = new CompilationEvent(null, compId, eventId);
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
