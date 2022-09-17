package ru.practicum.app.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.app.event.dto.EventFullDto;
import ru.practicum.app.event.dto.EventShortDto;
import ru.practicum.app.event.dto.NewEventDto;
import ru.practicum.app.event.dto.UpdateEventRequest;


import javax.validation.Valid;


@RestController
@RequestMapping
public class EventController {

    private final EventServiceImpl eventService;

    @Autowired
    public EventController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    /***
     *  Private: События. Закрытый API для работы с событиями:
     *  - Добавление нового события
     *  - Изменение события добавленного текущим пользователем
     */
    @PostMapping(value = "/users/{userId}/events")
    public EventShortDto create(@PathVariable(value = "userId") int userId,
                                @RequestBody @Valid NewEventDto newEventDto) {
        return eventService.create(userId, newEventDto);
    }

    @PatchMapping(value = "/users/{userId}/events")
    public UpdateEventRequest update(@PathVariable(value = "userId") int userId,
                                @RequestBody @Valid UpdateEventRequest updateEventRequest) {
        return eventService.update(userId, updateEventRequest);
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(@PathVariable(value = "userId") int userId,
                                    @PathVariable(value = "eventId") int eventId) {
        return eventService.cancelEvent(userId, eventId);
    }

}

