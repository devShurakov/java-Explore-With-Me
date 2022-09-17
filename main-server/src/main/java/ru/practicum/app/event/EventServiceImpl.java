package ru.practicum.app.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.app.category.Category;
import ru.practicum.app.category.CategoryRepository;
import ru.practicum.app.event.dto.EventFullDto;
import ru.practicum.app.event.dto.EventShortDto;
import ru.practicum.app.event.dto.NewEventDto;
import ru.practicum.app.event.dto.UpdateEventRequest;
import ru.practicum.app.event.model.Event;
import ru.practicum.app.event.model.EventStatus;
import ru.practicum.app.user.User;
import ru.practicum.app.user.UserCastomException;
import ru.practicum.app.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EventServiceImpl {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            UserRepository userRepository,
                            EventMapper eventMapper,
                            CategoryRepository categoryRepository) {

        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public EventShortDto create(int userId, NewEventDto newEventDto) {
        User user = findUserById(userId);
        Event event = eventRepository.save(eventMapper.mapToEvent(user, newEventDto));
        EventShortDto eventShortDto = eventMapper.mapToEventShortDto(event);
        log.info("создано новое событие {}", newEventDto.getTitle());
        return eventShortDto;
    }

    public UpdateEventRequest update(int userId, UpdateEventRequest updateEventRequest) {
        Event event = findEventById(updateEventRequest.getId());

        if (userId != event.getInitiator().getId()) {
            throw new UserCastomException("Пользователь не является создателем события");
        }

        Category category = categoryRepository
                .findById(updateEventRequest.getId())
                .orElseThrow(() -> new UserCastomException("Категория не найдена"));

        event.setAnnotation(updateEventRequest.getAnnotation());
        event.setCategory(category);
        event.setDescription(updateEventRequest.getDescription());
        event.setEventDate(updateEventRequest.getEventDate());
        event.setPaid(updateEventRequest.getPaid());
        event.setParticipantLimit(updateEventRequest.getParticipantLimit());
        event.setTitle(updateEventRequest.getTitle());
        event.setAnnotation(updateEventRequest.getAnnotation());

        eventRepository.save(event);

        UpdateEventRequest updatedEvent = eventMapper.mapToUpdateEventRequest(event);

        log.info("Событие с id: {} обновлено", updateEventRequest.getId());
        return updatedEvent;
    }

    public EventFullDto cancelEvent(int userId, int eventId) {
        Event event = findEventById(eventId);
        User user = findUserById(userId);

        if (event.getInitiator().getId().intValue() != user.getId()) {
            throw new UserCastomException("Пользователь не является создателем события");
        }
        event.setStatus(EventStatus.CANCELED);
        EventFullDto eventFullDto = eventMapper.mapToFullEventDto(eventRepository.save(event));
        log.info("событие {} отменено", event.getTitle());
        return eventFullDto;
    }

    public List<EventShortDto> getOwnerEvents(int userId, Integer from, Integer size) {
        findUserById(userId);
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        List<Event> eventsList = eventRepository.findAllByInitiator_Id(userId, pageable);
        List<EventShortDto> eventsShortDtoList = eventMapper.mapAlltoShortDto(eventsList);
        log.info("получен список событий пользователя");
        return eventsShortDtoList;
    }

    public EventFullDto getOwnerFullInfoEvents(int userId, int eventId) {
        if (userId != eventId) {
            throw new UserCastomException("Пользователь не является создателем события");
        }
        findUserById(userId);
        findEventById(eventId);
        Event ownerEvent = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new UserCastomException("пользователь не найден"));
        log.info("получена полная информация о событии пользователя");
        return eventMapper.mapToFullEventDto(ownerEvent);
    }

    public EventFullDto getFullInfoEvents(int eventId) {
        return eventMapper.mapToFullEventDto(findEventById(eventId));
    }

    public List<EventShortDto> getFilteredEvents(String text, List<String> categories, boolean paid,
                                                 LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                 boolean onlyAvailable, String sort, Integer from, Integer size) {
    return new ArrayList<>();
    }

    public User findUserById(int userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserCastomException("пользователь не найден"));
    }

    public Event findEventById(int eventId) {
        return eventRepository
                .findById(eventId)
                .orElseThrow(() -> new UserCastomException("событие не найдено"));
    }



}
