package ru.practicum.app.event.dto;

import ru.practicum.app.user.UserShortDto;

import java.time.LocalDateTime;

/***
 * Список идентификаторов событий входящих в подборку
 */

public class EventShortDto {

    int id;

    String annotation;

    int category; // todo id, name

    int confirmedRequests;

    LocalDateTime eventDate;

    UserShortDto initiator;

    boolean paid;

    String title;

    int views;

}
