package ru.practicum.app.event;

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

    private Integer id;

    private String annotation;

    private Integer category; // todo id, name

    private String description;

    private Location location;

    private String title;

//    Integer confirmedRequests;
    @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd HH:mm:ss") // TODO: 17.09.2022 //    дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
    private LocalDateTime eventDate;

//    UserShortDto initiator;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;
//    String title;
//
    Integer views;

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
