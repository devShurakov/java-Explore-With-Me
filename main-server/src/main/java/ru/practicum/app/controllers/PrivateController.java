package ru.practicum.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.app.dto.comment.CommentNewDto;
import ru.practicum.app.dto.comment.CommentDto;
import ru.practicum.app.dto.event.EventFullDto;
import ru.practicum.app.dto.event.EventShortDto;
import ru.practicum.app.dto.event.NewEventDto;
import ru.practicum.app.dto.request.ParticipationRequestDto;
import ru.practicum.app.dto.request.RequestDto;
import ru.practicum.app.exception.RequestCustomException;
import ru.practicum.app.model.event.UpdateEventRequest;
import ru.practicum.app.service.comment.CommentServiceImpl;
import ru.practicum.app.service.event.EventService;
import ru.practicum.app.service.request.RequestService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping
@Slf4j
public class PrivateController {


    private final EventService eventService;
    private final RequestService requestService;
    private final CommentServiceImpl commentServiceImpl;

    @Autowired
    public PrivateController(EventService eventService,
                             RequestService requestService,
                             CommentServiceImpl commentServiceImpl) {
        this.eventService = eventService;
        this.requestService = requestService;
        this.commentServiceImpl = commentServiceImpl;
    }

    /***
     *  Private: События. Закрытый API для работы с событиями:
     *  - Добавление нового события
     *  - Изменение события добавленного текущим пользователем
     *  - Отмена события добавленного пользователем
     *  - Получение событий, добавленных текущим пользователем
     *  - Получение полной информации о событии добавленном текущим пользователем
     */

    @PostMapping(value = "/users/{userId}/events")
    public EventFullDto create(@PathVariable(value = "userId") Integer userId,
                               @RequestBody @Valid NewEventDto newEventDto) {
        return eventService.create(userId, newEventDto);
    }

    @PatchMapping(value = "/users/{userId}/events")
    public EventFullDto update(@PathVariable(value = "userId") int userId,
                               @RequestBody UpdateEventRequest updateEventRequest) {
        return eventService.update(userId, updateEventRequest);
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(@PathVariable(value = "userId") int userId,
                                    @PathVariable(value = "eventId") int eventId) {
        return eventService.cancelEvent(userId, eventId);
    }

    @GetMapping(value = "/users/{userId}/events")
    public List<EventShortDto> getOwnerEvents(@PathVariable(value = "userId") String userId,
                                              @RequestParam(required = false) @Positive int from,
                                              @RequestParam(required = false) @Positive int size) {

        return eventService.getOwnerEvents(Integer.parseInt(userId), from, size);
    }

    @GetMapping(value = "/users/{userId}/events/{eventId}")
    public EventFullDto getOwnerFullInfoEvents(@PathVariable(value = "userId") int userId,
                                               @PathVariable(value = "eventId") int eventId) {
        return eventService.getOwnerFullInfoEvents(userId, eventId);
    }


    /***
     *  Private: Запросы на участие. Закрытый API для работы с запросами текущего пользователя на участие в событиях
     *  - Добавление запроса от текущего пользователя на участие в событии
     *  - Получение информации о заявках текущего пользователя на участие в чужих событиях
     *  - Отмена своего запроса на участие в событии
     */

    @PostMapping(value = "/users/{userId}/requests")
    public ParticipationRequestDto create(@PathVariable(value = "userId") Integer userId,
                                          @RequestParam("eventId") Integer eventId) {
        return requestService.create(userId, eventId);
    }

    @GetMapping(value = "/users/{userId}/requests")
    public Collection<RequestDto> getRequest(@PathVariable(value = "userId") int userId) {
        return requestService.getRequest(userId);
    }

    @PatchMapping(value = "/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable(value = "userId") Integer userId,
                                                 @PathVariable(value = "requestId", required = false) @NotNull Integer requestId) throws ValidationException {
        if (requestId == null) throw new RequestCustomException("Ошибка данных");
        return requestService.cancelRequest(userId, requestId);
    }


    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto acceptRequestByUser(@PathVariable Integer userId,
                                                       @PathVariable Integer eventId,
                                                       @PathVariable Integer reqId) {
        return requestService.acceptRequestByUser(userId, eventId, reqId);
    }


    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequestByUser(@PathVariable Integer userId,
                                                       @PathVariable Integer eventId,
                                                       @PathVariable Integer reqId) {
        return requestService.rejectRequestByUser(userId, eventId, reqId);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<RequestDto> getRequestByUser(@PathVariable Integer userId,
                                             @PathVariable Integer eventId) {

        return requestService.getRequestByUser(userId, eventId);
    }

    // ------------------------------ доп.функциональность ----------------------------
    @PostMapping("/users/{userId}/event/{eventId}/comment")
    public CommentDto createComment(@RequestBody CommentNewDto commentNewDto,
                                    @PathVariable Integer userId,
                                    @PathVariable Integer eventId) {
        return commentServiceImpl.createComment(commentNewDto, userId, eventId);
    }

    @PatchMapping("/users/comment/{commentId}")
    public CommentDto updateComment(@RequestBody CommentNewDto commentUpdateDto,
                                    @PathVariable Integer commentId) {
        return commentServiceImpl.updateComment(commentUpdateDto, commentId);
    }

    @GetMapping("/users/{userId}/comment")
    public List<CommentDto> getUserComments(@PathVariable Integer userId) {
        return commentServiceImpl.getUserComments(userId);
    }

    @DeleteMapping("/users/{userId}/event/comment/{commentId}")
    public void deleteComment(@PathVariable(value = "userId") int userId,
                              @PathVariable(value = "commentId") int commentId) {
        commentServiceImpl.deleteComment(commentId, userId);
    }

}
