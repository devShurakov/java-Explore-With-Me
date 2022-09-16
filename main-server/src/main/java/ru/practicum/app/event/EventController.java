package ru.practicum.app.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.app.event.dto.NewEventDto;


import javax.validation.Valid;


@RestController
@RequestMapping
public class EventController {

    private final EventServiceImpl eventService;

    @Autowired
    public EventController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @PostMapping(value = "/admin/events/{eventId}")
    public NewEventDto create(@RequestBody @Valid NewEventDto newEventDto) {
        return eventService.create(newEventDto);
    }



}
