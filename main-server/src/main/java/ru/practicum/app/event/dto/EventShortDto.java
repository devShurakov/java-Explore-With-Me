package ru.practicum.app.event.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/***
 * Список идентификаторов событий входящих в подборку
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {

    Integer id;

    String annotation;

    Integer category; // todo id, name

    String description;

    Location location;

    String title;

//    Integer confirmedRequests;
    @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd HH:mm:ss") // TODO: 17.09.2022 //    дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
    LocalDateTime eventDate;

//    UserShortDto initiator;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;
//    String title;
//
//    Integer views;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {

        private Float lat;

        private Float lon;
    }

}
