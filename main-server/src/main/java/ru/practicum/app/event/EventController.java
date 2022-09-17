package ru.practicum.app.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.app.event.dto.EventFullDto;
import ru.practicum.app.event.dto.EventShortDto;
import ru.practicum.app.event.dto.NewEventDto;
import ru.practicum.app.event.dto.UpdateEventRequest;


import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping
@Validated
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
     *  - Отмена события добавленного пользователем
     *  - Получение событий, добавленных текущим пользователем
     *  - Получение полной информации о событии добавленном текущим пользователем
     */

    // Добавление нового события
    @PostMapping(value = "/users/{userId}/events")
    public EventShortDto create(@PathVariable(value = "userId") int userId,
                                @RequestBody @Valid NewEventDto newEventDto) {
        return eventService.create(userId, newEventDto);
    }

    //изменение события добавленного текущим пользователем
    @PatchMapping(value = "/users/{userId}/events")
    public UpdateEventRequest update(@PathVariable(value = "userId") int userId,
                                     @RequestBody @Valid UpdateEventRequest updateEventRequest) {
        return eventService.update(userId, updateEventRequest);
    }

    // Отмена события добавленного пользователем
    @PatchMapping(value = "/users/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(@PathVariable(value = "userId") int userId,
                                    @PathVariable(value = "eventId") int eventId) {
        return eventService.cancelEvent(userId, eventId);
    }

    //Получение событий, добавленных текущим пользователем
    @GetMapping(value = "/users/{userId}/events")
    public List<EventShortDto> getOwnerEvents(@PathVariable(value = "userId") int userId,
                                              @RequestParam(required = false) @Positive Integer from,
                                              @RequestParam(required = false) @Positive Integer size) {
        return eventService.getOwnerEvents(userId, from, size);
    }

    //Получение полной информации о событии добавленном текущим пользователем
    @GetMapping(value = "/users/{userId}/events/{eventId}")
    public EventFullDto getOwnerFullInfoEvents(@PathVariable(value = "userId") int userId,
                                                @PathVariable(value = "eventId") int eventId) {
        return eventService.getOwnerFullInfoEvents(userId, eventId);
    }

    /***
     *  - Публичный API для работы с событиями:
     *  - Получение событий с возможностью фильтрации
     *  - Получение подробной информации об опубликованном событии по его идентификатору
     */

    @GetMapping(value = "/events")
    public List<EventShortDto> getFilteredEvents(@RequestParam(required = false) String text,
                                                 @RequestParam(required = false) List<String> categories,
                                                 @RequestParam(required = false) boolean paid,
                                                 @RequestParam(required = false) LocalDateTime rangeStart,
                                                 @RequestParam(required = false) LocalDateTime rangeEnd,
                                                 @RequestParam(required = false) boolean onlyAvailable,
                                                 @RequestParam(required = false) String sort,
                                                 @RequestParam(required = false) @Positive Integer from,
                                                 @RequestParam(required = false) @Positive Integer size) {
        return eventService.getFilteredEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    //Получение полной информации о событии добавленном текущим пользователем
    @GetMapping(value = "/events/{eventId}")
    public EventFullDto getFullInfoEvents(@PathVariable(value = "eventId") int eventId) {
        return eventService.getFullInfoEvents(eventId);
    }

}

