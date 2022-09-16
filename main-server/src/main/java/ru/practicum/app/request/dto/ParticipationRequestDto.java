package ru.practicum.app.request.dto;

import java.time.LocalDateTime;

/***
 * Запрос на участие в событии
 */

public class ParticipationRequestDto {

    int id;

    LocalDateTime created;

    int event;

    int requester;

    String status;

}
