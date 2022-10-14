package ru.practicum.app.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {


    private String annotation;

    private Integer category;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private String title;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {

        private Float lat;

        private Float lon;
    }

}
