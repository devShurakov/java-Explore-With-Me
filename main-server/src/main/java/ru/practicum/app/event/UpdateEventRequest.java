package ru.practicum.app.event;

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


//    @NotNull
//    @Size(min = 20, max = 2000)
    private String annotation;
//    @NotNull
    private Integer category;
//    @Size(min = 20, max = 7000)
//    @NotNull
    private String description;
//    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Integer eventId;
//    @NotNull
//    private Location location;
    private Boolean paid;
    private Integer participantLimit;
//    private Boolean requestModeration;
//    @NotNull
//    @Size(min = 3, max = 120)
    private String title;

//    @Data
//    public static class Location {
//        private float lat;
//        private float lon;
//    }

//    private Integer id;
//    @NotNull
//    @Size(min = 20, max = 2000)
//    private String annotation;
//    @NotNull
//    private Integer category;
//    @Size(min = 20, max = 7000)
//    @NotNull
//    private String description;
//
//    @NotNull
//    @Size(min = 3, max = 120)
//    private String title;
//
//    @NotNull
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime eventDate;
//
//    private Boolean paid;
//
//    private Integer participantLimit;

}
