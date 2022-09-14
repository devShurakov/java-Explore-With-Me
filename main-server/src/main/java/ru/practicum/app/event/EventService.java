package ru.practicum.app.event;

import ru.practicum.app.user.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventService {
    EventFullDto create(int userId, NewEventDto newEventDto);

    boolean isDateAfterTwoHours(LocalDateTime date);

    EventFullDto update(int userId, UpdateEventRequest updateEventRequest);

    EventFullDto cancelEvent(int userId, int eventId);

    List<EventShortDto> getOwnerEvents(int userId, Integer from, Integer size);

    EventFullDto getOwnerFullInfoEvents(int userId, int eventId);

    EventFullDto getFullInfoEvents(int eventId);

    User findUserById(int userId);

    Event findEventById(int eventId);

    EventFullDto updateEvent(int eventId, AdminUpdateEventRequest adminUpdateEventRequest);

    EventFullDto publishEvent(Integer eventId);

    EventFullDto rejectEvent(int eventId);

    List<EventShortDto> getEventsByIds(List<Integer> ids);

    Collection<EventFullDto> getEventByAdmin(List<Long> users, List<String> statesStr, List<Long> categories,
                                             String rangeStart, String rangeEnd, int from, int size);

    Collection<EventShortDto> getFilteredEvents(String text,
                                                List<Integer> categories,
                                                Boolean paid,
                                                String rangeStart,
                                                String rangeEnd,
                                                Boolean onlyAvailable,
                                                EventSortValues sort,
                                                int from,
                                                int size);
}
