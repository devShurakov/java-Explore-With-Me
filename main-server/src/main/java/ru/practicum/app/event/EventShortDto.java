package ru.practicum.app.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

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

    private Category category; // todo id, name
//    private Integer category; // todo id, name

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

//    User initiator;
    UserShortDto initiator;

    private Integer confirmedRequests; //todo проверить добавил 11 октября

    private String description;

//    private Location location;

    private String title; //todo не было


//
//    private Boolean paid;
//
private Integer views;

//    @Getter
//    @Setter
//    @Builder
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class Location {
//
//        private Float lat;
//
//        private Float lon;
//    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {

        private Integer id;

        private String name;

    }

//    @Getter
//    @Setter
//    @Builder
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class User {
//
//        private Integer id;
//
//        private String name;
//
//        private String email;
//
//    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserShortDto {

        private Integer id;

        private String name;

//        private String email;

    }

}
