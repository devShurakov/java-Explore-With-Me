package ru.practicum.app.mapper.event;

import ru.practicum.app.dto.event.EventFullDto;
import ru.practicum.app.dto.event.EventShortDto;
import ru.practicum.app.dto.event.NewEventDto;
import ru.practicum.app.model.event.Event;
import ru.practicum.app.model.event.EventStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventMapper {

    public static Event mapFromNewEvent(NewEventDto newEventDto) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setDescription(newEventDto.getDescription());
        event.setLat(newEventDto.getLocation().getLat());
        event.setLon(newEventDto.getLocation().getLon());
        event.setTitle(newEventDto.getTitle());
        event.setEventDate(newEventDto.getEventDate());
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setConfirmedRequests(0);
        event.setCreated(LocalDateTime.now());
        event.setStatus(EventStatus.PENDING);
        event.setViews(0);
        event.setPublishedOn(LocalDateTime.now());
        return event;
    }

    public static EventShortDto mapToEventShortDto(Event event) {
        EventShortDto eventShortDto = new EventShortDto();

        eventShortDto.setId(event.getEventId());
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(new EventShortDto.Category(event.getCategory().getCategoryId(),
                event.getCategory().getName()));
        eventShortDto.setDescription(event.getDescription());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setViews(event.getViews());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setInitiator(new EventShortDto.UserShortDto(event.getInitiator().getId(),
                event.getInitiator().getName()));
        return eventShortDto;
    }

    public static EventFullDto mapToFullEventDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setId(event.getEventId());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(new EventFullDto.Category(event.getCategory().getCategoryId(), event.getCategory().getName()));
        eventFullDto.setConfirmedRequests(event.getConfirmedRequests());
        eventFullDto.setCreatedOn(event.getCreated());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setLocation(new EventFullDto.Location(event.getLat(), event.getLon()));
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setPublishedOn(event.getPublishedOn());
        eventFullDto.setInitiator(new EventFullDto.UserShortDto(
                event.getInitiator().getId(),
                event.getInitiator().getName())
        );
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setState(event.getStatus());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setViews(event.getViews());
        return eventFullDto;
    }

    public static List<EventShortDto> mapAlltoShortDto(List<Event> eventsList) {
        List<EventShortDto> eventShortDtoDtoList = new ArrayList<>();
        for (Event event : eventsList) {
            eventShortDtoDtoList.add(mapToEventShortDto(event));
        }
        return eventShortDtoDtoList;
    }
}
