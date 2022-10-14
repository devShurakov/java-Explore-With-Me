package ru.practicum.app.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/***
 * Список идентификаторов событий входящих в подборку
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {

    private Integer id;

    private String annotation;

    private Category category;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    UserShortDto initiator;

    private Integer confirmedRequests;

    private String description;

    private String title;

    private Integer views;

    private Boolean paid;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {

        private Integer id;

        private String name;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserShortDto {

        private Integer id;

        private String name;

    }

}
