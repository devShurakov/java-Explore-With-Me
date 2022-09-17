package ru.practicum.app.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/***
 * Данные для изменения информации о событии
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {


    Integer id;

    String annotation;

    Integer category; // TODO: 17.09.2022   id, name

    String description;

    EventShortDto.Location location;

    String title;

    //    Integer confirmedRequests;
    @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

//    UserShortDto initiator;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;
}
