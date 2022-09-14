package event.dto;

import java.time.LocalDateTime;

public class NewEventDto {

    String annotation;

    int category;

    String description;

    LocalDateTime eventDate;

    String location; //todo cо звездочкой

    boolean paid;

    int participantLimit;

    int requestModeration;

    String title; // todo cо звездочкой

}
