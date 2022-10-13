package ru.practicum.app.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.app.model.category.Category;
import ru.practicum.app.model.event.*;
import ru.practicum.app.repository.category.CategoryRepository;
import ru.practicum.app.dto.event.EventFullDto;
import ru.practicum.app.dto.event.EventShortDto;
import ru.practicum.app.dto.event.NewEventDto;
import ru.practicum.app.exception.EventCanNotBeException;
import ru.practicum.app.exception.OperationException;
import ru.practicum.app.mapper.event.EventMapper;
import ru.practicum.app.model.user.User;
import ru.practicum.app.exception.UserCastomException;
import ru.practicum.app.repository.event.EventRepository;
import ru.practicum.app.repository.user.UserRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

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

    @Override
    public EventFullDto create(int userId, NewEventDto newEventDto) {
        isDateAfterTwoHours(newEventDto.getEventDate());
        User user = findUserById(userId);
        Event event = eventMapper.mapFromNewEvent(newEventDto);
        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow();
        event.setCategory(category);
        event.setInitiator(user);
        Event eventToReturn = eventRepository.save(event);
        EventFullDto eventShortDto = eventMapper.mapToFullEventDto(eventToReturn);
        log.info("событие {} создано", event.getTitle());
        return eventShortDto;
    }

    @Override
    public boolean isDateAfterTwoHours(LocalDateTime date) {
        if (date.isAfter(LocalDateTime.now().plusHours(2))) {
            return true;
        } else {
            throw new EventCanNotBeException("Дата события не может быть раньше ");
        }
    }

    @Override
    public EventFullDto update(int userId, UpdateEventRequest updateEventRequest) {
        Event event = findEventById(updateEventRequest.getEventId());

        if (userId != event.getInitiator().getId()) {
            throw new UserCastomException("Пользователь не является создателем события");
        }

        Category category = categoryRepository
                .findById(updateEventRequest.getCategory())
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

        EventFullDto updatedEvent = eventMapper.mapToFullEventDto(event);
        log.info("событие {} обновлено", event.getTitle());
        return updatedEvent;
    }

    @Override
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

    @Override
    public List<EventShortDto> getOwnerEvents(int userId, Integer from, Integer size) {
        findUserById(userId);
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> eventsList = eventRepository.findAllByInitiatorId(userId, pageable);
        List<EventShortDto> eventsShortDtoList = eventMapper.mapAlltoShortDto(eventsList);
        log.info("получен список событий пользователя");
        return eventsShortDtoList;
    }

    @Override
    public EventFullDto getOwnerFullInfoEvents(int userId, int eventId) {
        findUserById(userId);
        findEventById(eventId);
        Event ownerEvent = eventRepository.findByIdAndInitiator_Id(eventId, userId);
        log.info("получен полный список событий пользователя");
        return eventMapper.mapToFullEventDto(ownerEvent);
    }

    @Override
    public EventFullDto getFullInfoEvents(int eventId) {
        log.info("получена полная информация о событии");
        return eventMapper.mapToFullEventDto(findEventById(eventId));
    }

    @Override
    public User findUserById(int userId) {
        log.info("полученена информация о пользователе");
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserCastomException("пользователь не найден"));
    }

    @Override
    public Event findEventById(int eventId) {
        log.info("полученена информация о событии");
        return eventRepository
                .findById(eventId)
                .orElseThrow(() -> new UserCastomException("событие не найдено"));
    }

    @Override
    public EventFullDto updateEvent(int eventId, AdminUpdateEventRequest adminUpdateEventRequest) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new UserCastomException("событие не найдено"));

        EventStatus state = event.getStatus();

        if (adminUpdateEventRequest.getTitle() != null) event.setTitle(adminUpdateEventRequest.getTitle());
        if (adminUpdateEventRequest.getLocation() != null) event.setLon(adminUpdateEventRequest.getLocation().getLon());
        if (adminUpdateEventRequest.getLocation() != null) event.setLat(adminUpdateEventRequest.getLocation().getLat());

        if (adminUpdateEventRequest.getAnnotation() != null)
            event.setAnnotation(adminUpdateEventRequest.getAnnotation());
        if (adminUpdateEventRequest.getDescription() != null)
            event.setDescription(adminUpdateEventRequest.getDescription());
        if (adminUpdateEventRequest.getEventDate() != null)
            event.setEventDate(adminUpdateEventRequest.getEventDate());
        if (adminUpdateEventRequest.getPaid() != null) event.setPaid(adminUpdateEventRequest.getPaid());
        if (adminUpdateEventRequest.getCategory() != null) {
            event.setCategory(new Category(adminUpdateEventRequest.getCategory(), null));
        }
        if (adminUpdateEventRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(adminUpdateEventRequest.getParticipantLimit());
        }
        if (adminUpdateEventRequest.getRequestModeration() != null) {
            event.setRequestModeration(adminUpdateEventRequest.getRequestModeration());
        }

        event.setStatus(EventStatus.PENDING);
        Event updatedEvent = eventRepository.save(event);
        log.info("событие обновленое");
        return eventMapper.mapToFullEventDto(updatedEvent);
    }

    @Override
    public EventFullDto publishEvent(Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new UserCastomException("событие не найдено"));

        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            String message = "Только события которые начинаются на час больше текущенго времени могут быть опубликованы.";
            throw new OperationException(message);
        }
        if (!EventStatus.PENDING.equals(event.getStatus())) {
            String message = "Только события в статусе pending  могут быть опубликованы.";
            throw new OperationException(message);
        }
        event.setStatus(EventStatus.PUBLISHED);
        Event publishedEvent = eventRepository.save(event);
        log.info("событие опубликовано");
        return eventMapper.mapToFullEventDto(publishedEvent);
    }

    @Override
    public EventFullDto rejectEvent(int eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new UserCastomException("событие не найдено"));
        if (!EventStatus.PENDING.equals(event.getStatus())) {
            String message = "Только события в статусе pending  могут быть опубликованы.";
            throw new OperationException(message);
        }
        event.setStatus(EventStatus.CANCELED);
        Event rejectedEvent = eventRepository.save(event);
        log.info("событие отменено");
        return eventMapper.mapToFullEventDto(rejectedEvent);
    }

    @Override
    public List<EventShortDto> getEventsByIds(List<Integer> ids) {
        log.info("событие получено по id");
        return eventRepository.findAllById(ids)
                .stream()
                .map(eventMapper::mapToEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<EventFullDto> getEventByAdmin(List<Long> users, List<String> statesStr, List<Long> categories,
                                                    String rangeStart, String rangeEnd, int from, int size) {
        LocalDateTime start;
        LocalDateTime end;
        boolean isUsers = users.isEmpty() ? false : true;
        boolean isCat = categories.isEmpty() ? false : true;
        boolean isState = statesStr.isEmpty() ? false : true;

        if (rangeStart.isEmpty()) {
            start = LocalDateTime.now();
        } else {
            start = LocalDateTime.parse(URLDecoder.decode(rangeStart, StandardCharsets.UTF_8),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        if (rangeEnd.isEmpty()) {
            end = LocalDateTime.now().minusYears(100);
        } else {
            end = LocalDateTime.parse(URLDecoder.decode(rangeEnd, StandardCharsets.UTF_8),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        log.info("плучена информация о событии");
        return eventRepository.findByAdmin(isUsers, users, isState, statesStr, isCat, categories, start, end,
                        PageRequest.of(from, size))
                .stream()
                .map(eventMapper::mapToFullEventDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<EventShortDto> getFilteredEvents(String text,
                                                       List<Integer> categories,
                                                       Boolean paid,
                                                       String rangeStart,
                                                       String rangeEnd,
                                                       Boolean onlyAvailable,
                                                       EventSortValues sort,
                                                       int from,
                                                       int size) {
        LocalDateTime start;
        LocalDateTime end;
        Boolean isCategories = categories.isEmpty() ? false : true;

        if (rangeStart.isEmpty()) {
            start = LocalDateTime.now();
        } else {
            start = LocalDateTime.parse(URLDecoder.decode(rangeStart, StandardCharsets.UTF_8),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        if (rangeEnd.isEmpty()) {
            end = LocalDateTime.now().minusYears(100);
        } else {
            end = LocalDateTime.parse(URLDecoder.decode(rangeEnd, StandardCharsets.UTF_8),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        var events = eventRepository.find(text, isCategories, categories, paid, start, end,
                onlyAvailable, PageRequest.of(from, size));

        if (sort != null) {
            if (sort == EventSortValues.EVENT_DATE) {
                events.stream().sorted(Comparator.comparing(Event::getEventDate));
            }
            if (sort == EventSortValues.VIEWS) {
                events.stream().sorted(Comparator.comparing(Event::getEventDate));
            }
        }
        log.info("плучена информация о событии с использованием фильтров");
        return events.stream()
                .map(event -> eventMapper.mapToEventShortDto(event))
                .collect(Collectors.toList());
    }


}
