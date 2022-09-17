package ru.practicum.app.event;

import org.springframework.stereotype.Component;
import ru.practicum.app.category.Category;
import ru.practicum.app.event.dto.EventShortDto;
import ru.practicum.app.event.dto.NewEventDto;
import ru.practicum.app.event.dto.UpdateEventRequest;
import ru.practicum.app.event.model.Event;
import ru.practicum.app.event.model.EventStatus;
import ru.practicum.app.user.User;

import java.time.LocalDateTime;

@Component
public class EventMapper {
    public Event mapToEvent(User initiator,NewEventDto newEventDto) {
        Event event = new Event();

        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(new Category(newEventDto.getCategory().getId(),newEventDto.getCategory().getName()));
        event.setConfirmedRequests(0); // TODO: 17.09.2022   требуется ли вносить для всех
        event.setCreated(newEventDto.getCreatedOn());
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setLat(newEventDto.getLocation().getLat());// TODO: 17.09.2022  location
        event.setLon(newEventDto.getLocation().getLon());
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setPublishedOn(LocalDateTime.now()); // TODO: 17.09.2022  время публикации
        event.setInitiator(initiator);
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setStatus(EventStatus.WAITING);
        event.setTitle(newEventDto.getTitle());
        event.setViews(0);
        return event;
    }

    public EventShortDto mapToEventShortDto(Event event) {
        EventShortDto eventShortDto = new EventShortDto();

        eventShortDto.setId(event.getId());
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(event.getCategory().getId());
        eventShortDto.setDescription(event.getDescription());
        eventShortDto.setLocation(new EventShortDto.Location(event.getLat(),event.getLon()));
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setParticipantLimit(event.getParticipantLimit());
        eventShortDto.setRequestModeration(event.getRequestModeration());
        return eventShortDto;
    }

    public UpdateEventRequest mapToUpdateEventRequest(Event event) {
        UpdateEventRequest updateEventRequest = new UpdateEventRequest();

        updateEventRequest.setId(event.getId());
        updateEventRequest.setAnnotation(event.getAnnotation());
        updateEventRequest.setCategory(event.getCategory().getId());
        updateEventRequest.setDescription(event.getDescription());
        updateEventRequest.setEventDate(event.getEventDate());
        updateEventRequest.setPaid(event.getPaid());
        updateEventRequest.setParticipantLimit(event.getParticipantLimit());
        updateEventRequest.setTitle(event.getTitle());
        return updateEventRequest;
    }

}
