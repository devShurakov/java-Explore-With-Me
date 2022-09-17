package ru.practicum.app.event.dto;

import lombok.*;
import ru.practicum.app.event.model.EventStatus;
import ru.practicum.app.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {

    Integer id;

    String annotation;

    Category category;

    Integer confirmedRequests;

    LocalDateTime created;

    String description;

    LocalDateTime eventDate;

    User initiator;

    Location location;

    Boolean paid;

    Integer participantLimit;

    LocalDateTime publishedOn;

    Boolean requestModeration;

    EventStatus status;

    String title;

    Integer views;

    @Builder
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
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Initiator extends User {

        private Integer id;

        private String name;
    }

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
