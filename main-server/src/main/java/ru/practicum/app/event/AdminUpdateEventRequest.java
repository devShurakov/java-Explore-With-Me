package ru.practicum.app.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateEventRequest {

    @NotNull
    @Size(min = 20, max = 2100)
    String annotation;

    @NotNull
    Integer category;

    @Size(min = 20, max = 5000)
    @NotNull
    String description;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    Location location;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    @NotNull
    @Size(min = 3, max = 120)
    String title;

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
