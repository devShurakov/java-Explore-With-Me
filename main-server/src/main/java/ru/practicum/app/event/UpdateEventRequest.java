package ru.practicum.app.event;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/***
 * Данные для изменения информации о событии
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {


    private Integer id;

    private String annotation;

    private Integer category; // TODO: 17.09.2022   id, name

    private String description;

    private Location location;

    private String title;

    //    Integer confirmedRequests;
    @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

//    UserShortDto initiator;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

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
