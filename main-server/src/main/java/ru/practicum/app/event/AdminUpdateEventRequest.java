package ru.practicum.app.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.app.category.CategoryDto;
import ru.practicum.app.user.UserShortDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateEventRequest {

    Integer id;

    String annotation;

    Integer category;

    CategoryDto confirmedRequests;

    String createdOn;

    String description;

    LocalDateTime eventDate;

    UserShortDto initiator;

    String location; //todo

    Boolean paid;

    Integer participantLimit;

    String publishedOn;

    Boolean requestModeration;

    String state;

    String title;

    Integer views;
}
