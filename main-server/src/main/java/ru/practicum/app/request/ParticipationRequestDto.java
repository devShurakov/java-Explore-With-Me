package ru.practicum.app.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/***
 * Запрос на участие в событии
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {

    Integer id;

    LocalDateTime created;

    Integer event;

    Integer requester;

    String status;

}
