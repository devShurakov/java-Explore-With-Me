package ru.practicum.app.event;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.app.user.UserShortDto;

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

    private Integer confirmedRequests;

    private String description;

    private Location location;

    private String title;

    @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    UserShortDto initiator;

    private Boolean paid;

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
