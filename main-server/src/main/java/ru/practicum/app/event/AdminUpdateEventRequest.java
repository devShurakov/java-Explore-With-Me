package ru.practicum.app.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.app.location.Location;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateEventRequest {

//    Integer id;

    String annotation;

    Integer category;

    String description;

    LocalDateTime eventDate;

    Location location; //todo

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    String title;

//    CategoryDto confirmedRequests;
//
//    String createdOn;
//
//
//    UserShortDto initiator;
//
////    String location; //todo
//
//
//    String publishedOn;
//
////    Boolean requestModeration;
//
//    String state;

//    String title;
//
//    Integer views;
}
