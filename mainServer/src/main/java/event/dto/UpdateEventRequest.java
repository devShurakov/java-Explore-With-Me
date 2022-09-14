package event.dto;

import java.time.LocalDateTime;

/***
 * Данные для изменения информации о событии
 */

public class UpdateEventRequest {

    String annotation;

    int category;

    String description;

    LocalDateTime eventDate;

    int eventId;

    boolean paid;

    //лимит пользователей
    int participantLimit;

    String title;
}
