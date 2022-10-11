package ru.practicum.app.event;

import org.springframework.stereotype.Component;
import ru.practicum.app.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventMapper {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Event mapToEvent(User initiator, NewEventDto newEventDto) {
        Event event = new Event();
        event.setEventId(null);
        event.setAnnotation(newEventDto.getAnnotation());
        event.setConfirmedRequests(0);
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setCreated(LocalDateTime.now());
        event.setLat(newEventDto.getLocation().getLat());
        event.setLon(newEventDto.getLocation().getLon());
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setPublishedOn(LocalDateTime.now());
        event.setInitiator(initiator);
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setStatus(EventStatus.PENDING);
        event.setTitle(newEventDto.getTitle());
        event.setViews(0);
        return event;
    }

    public Event mapFromNewEvent(NewEventDto newEventDto) {
        Event event = new Event();
//        event.setEventId(null);
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
//        event.setConfirmedRequests(0);
//        event.setEventDate(newEventDto.getEventDate());
//        event.setCreated(LocalDateTime.now());
//        event.setLat(newEventDto.getLocation().getLat());
//        event.setLon(newEventDto.getLocation().getLon());
//        event.setPaid(newEventDto.getPaid());
//        event.setParticipantLimit(newEventDto.getParticipantLimit());
//        event.setPublishedOn(LocalDateTime.now());
//        event.setInitiator(initiator);
//        event.setRequestModeration(newEventDto.getRequestModeration());
//        event.setStatus(EventStatus.PENDING);
//        event.setTitle(newEventDto.getTitle());
//        event.setViews(0);
        return event;
    }

    public EventShortDto mapToEventShortDto(Event event) {
        EventShortDto eventShortDto = new EventShortDto();

        eventShortDto.setId(event.getEventId());
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(new EventShortDto.Category(event.getCategory().getCategoryId(),
                event.getCategory().getName()));
//        eventShortDto.setCategory(event.getCategory().getCategoryId());
        eventShortDto.setDescription(event.getDescription());
//        eventShortDto.setLocation(new EventShortDto.Location(event.getLat(), event.getLon()));
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setEventDate(event.getEventDate());
//        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setInitiator(new EventShortDto.User(event.getInitiator().getId(), event.getInitiator().getName(),
                event.getInitiator().getEmail()));
        return eventShortDto;
    }

    public UpdateEventRequest mapToUpdateEventRequest(Event event) {
        UpdateEventRequest updateEventRequest = new UpdateEventRequest();

        updateEventRequest.setEventId(event.getEventId());
        updateEventRequest.setAnnotation(event.getAnnotation());
//        updateEventRequest.setCategory(event.getCategory().getCategoryId());
        updateEventRequest.setDescription(event.getDescription());
        updateEventRequest.setEventDate(event.getEventDate());
        updateEventRequest.setPaid(event.getPaid());
        updateEventRequest.setParticipantLimit(event.getParticipantLimit());
        updateEventRequest.setTitle(event.getTitle());
        return updateEventRequest;
    }

    public EventFullDto mapToFullEventDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();

        eventFullDto.setId(event.getEventId());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(new EventFullDto.Category(event.getCategory().getCategoryId(), event.getCategory().getName()));
        eventFullDto.setConfirmedRequests(event.getConfirmedRequests());
        eventFullDto.setCreated(event.getCreated());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setLocation(new EventFullDto.Location(event.getLat(), event.getLon()));
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setPublishedOn(event.getPublishedOn());
        eventFullDto.setInitiator(new EventFullDto.UserShortDto(
                event.getInitiator().getId(),
                event.getInitiator().getName())
//                event.getInitiator().getEmail())
        );
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setState(event.getStatus());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setViews(event.getViews());
        return eventFullDto;
    }

    public List<EventShortDto> mapAlltoShortDto(List<Event> eventsList) {
        List<EventShortDto> eventShortDtoDtoList = new ArrayList<>();
        for (Event event : eventsList) {
            eventShortDtoDtoList.add(mapToEventShortDto(event));
        }
        return eventShortDtoDtoList;
    }
}
