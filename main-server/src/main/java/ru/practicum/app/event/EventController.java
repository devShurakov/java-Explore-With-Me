package ru.practicum.app.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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
                                @RequestBody NewEventDto newEventDto) {
        return eventService.create(Math.toIntExact(userId), newEventDto);
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
    public List<EventShortDto> getOwnerEvents(@PathVariable(value = "userId") String userId,
                                              @RequestParam(required = false) @Positive int from,
                                              @RequestParam(required = false) @Positive int size) {

        return eventService.getOwnerEvents(Integer.parseInt(userId), from, size);
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
     *  - редактирование события
     */

    @GetMapping(value = "/events")
    public List<EventShortDto> getEvents(@RequestParam(required = false) String text,
                                                 @RequestParam(required = false) int[] categories,
                                                 @RequestParam(required = false) Boolean paid,
                                                 @RequestParam(required = false) String rangeStart,
                                                 @RequestParam(required = false) String rangeEnd,
                                                 @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                                 @RequestParam(required = false) String sort,
                                                 @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                 @Positive @RequestParam(defaultValue = "10") int size,
                                                 HttpServletRequest request) {
        return eventService.getEvents(request, text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
//    }

//    @GetMapping("/events")
//    public List<EventShortDto> getEvents(@RequestParam(required = false) String text,
//                                         @RequestParam(required = false) Integer categoryId,
//                                         @RequestParam(required = false) Boolean paid,
//                                         @RequestParam(required = false) String rangeStart,
//                                         @RequestParam(required = false) String rangeEnd,
//                                         @RequestParam(required = false) Boolean onlyAvailable,
//                                         @RequestParam(required = false) String sort,
//                                         @RequestParam(defaultValue = "0", required = false) Integer from,
//                                         @RequestParam(defaultValue = "10", required = false) Integer size,
//                                         HttpServletRequest request) {
//        log.debug("Публичный запрос на просмотр всех событий.");

//        return eventService.getEvents(text, categoryId, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }

    //Получение полной информации о событии добавленном текущим пользователем
    @GetMapping(value = "/events/{eventId}")
    public EventFullDto getFullInfoEvents(@PathVariable(value = "eventId") int eventId) {
        return eventService.getFullInfoEvents(eventId);
    }


    /*** Admin: События. API для работы с событиями
     * - поиск событий
     * - редактирование события
     * - публикация события
     * - отклонить событие
     */
//    @GetMapping("admin/events") // TODO: 03.10.2022 не работает
//    public List<EventFullDto> getEventsAdmin(@RequestParam int[] users,
//                                             @RequestParam String[] states,
//                                             @RequestParam int[] categories,
//                                             @RequestParam String rangeStart,
//                                             @RequestParam String rangeEnd,
//                                             @RequestParam int from,
//                                             @RequestParam int size) {
////        log.info("Администратор запросил список событий с параметрами users={}, states={}, categories={}, rangeStart={}, rangeEnd={}, from={}, size={}",
////                users, states, categories, rangeStart, rangeEnd, from, size);
//        return eventService.getEventsAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
//    }

    @PutMapping(value = "/admin/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable int eventId,
                                    @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        return eventService.updateEvent(eventId, adminUpdateEventRequest);
    }

    @PatchMapping(value = "/admin/events/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable int eventId) {
        return eventService.publishEvent(eventId);
    }

    @PatchMapping(value = "/admin/events/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable int eventId) {
        return eventService.rejectEvent(eventId);
    }

}

