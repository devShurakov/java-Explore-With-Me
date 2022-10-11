package ru.practicum.app.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {

    private Integer id;
    @NotNull
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private Integer category;
    @Size(min = 20, max = 7000)
    @NotNull
    private String description;
    @NotNull
    private Location location;
    @NotNull
    @Size(min = 3, max = 120)
    private String title;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;



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
