package ru.practicum.app.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Validated
public class RequestController {

    private final RequestServiceImpl requestService;

    @Autowired
    public RequestController(RequestServiceImpl requestService) {
        this.requestService = requestService;
    }

    /***
     *  Private: Запросы на участие. Закрытый API для работы с запросами текущего пользователя на участие в событиях
     *  - Добавление запроса от текущего пользователя на участие в событии
     *  - Получение информации о заявках текущего пользователя на участие в чужих событиях
     *  - Отмена своего запроса на участие в событии
     */

    //Добавление запроса от текущего пользователя на участие в событии
    @PostMapping(value = "/users/{userId}/requests")
    public ParticipationRequestDto create(@PathVariable(value = "userId") Integer userId,
                                          @RequestParam("eventId") Integer eventId) {
        return requestService.create(userId, eventId);
    }

    //Получение информации о заявках текущего пользователя на участие в чужих событиях
    @GetMapping(value = "/users/{userId}/requests")
    public List<ParticipationRequestDto> getRequest(@RequestParam (value = "userId")  int userId) {
        return requestService.getRequest(userId);
    }

    //Отмена своего запроса на участие в событии
    @PatchMapping(value = "/users/{userId}/requests")
    public ParticipationRequestDto cancelRequest(@PathVariable Integer userId,
                                                 @PathVariable Integer requestId) {
        return requestService.cancelRequest(userId, requestId);
    }

}
