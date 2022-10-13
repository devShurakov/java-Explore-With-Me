package ru.practicum.app.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/***
 * Данные для изменения информации о событии
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {


    private String annotation;

    private Integer category;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Integer eventId;

    private Boolean paid;

    private Integer participantLimit;

    private String title;

}
