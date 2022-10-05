package ru.practicum.app.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {

    private Integer id;

    @NotNull
    private String annotation;

    @NotNull
    private Integer category;

//    @NotNull
//    private Category category;
//
//    private User initiator;
//
//    @NotNull
//    private Location location;
//    @NotNull
//    private NewEventDto.Category category;

//    private NewEventDto.User initiator;

    @NotNull
    private NewEventDto.Location location;

    @NotNull
    private String title;

//    @NotNull
//    private Integer confirmedRequests;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdOn;

    @NotNull
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private Boolean paid;

    @NotNull
    private Integer participantLimit;

    @NotNull
    private Boolean requestModeration;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime publishedOn;

//    private Integer views;

//    @Builder
//    @Getter
//    @Setter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class Category {
//
//        private Integer id;
//
//        private String name;
//    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {

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
