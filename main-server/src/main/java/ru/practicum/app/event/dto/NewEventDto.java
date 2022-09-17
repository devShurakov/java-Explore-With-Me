package ru.practicum.app.event.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
//@ConfigurationProperties(prefix="app.properties") // TODO: 17.09.2022 мб нужно настроить
public class NewEventDto {

    Integer id;

    String annotation;

    Category category;

    Initiator initiator;

    Location location;

    String title;

    Integer confirmedRequests;

    @Future // TODO: 17.09.2022 сделать на два + часа позднее
    @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;

    String description;

    @Future // TODO: 17.09.2022 сделать на два часа позднее
    @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {

        private Integer id;

        private String name;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Initiator {

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
