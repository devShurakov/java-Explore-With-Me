package ru.practicum.app.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.app.category.Category;
import ru.practicum.app.category.CategoryRepository;
import ru.practicum.app.exception.OperationException;
import ru.practicum.app.user.User;
import ru.practicum.app.exception.UserCastomException;
import ru.practicum.app.user.UserRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


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
        Event event = eventMapper.mapToEvent(user, newEventDto);
        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow();

        event.setCategory(category);
        Event eventToReturn = eventRepository.save(event);
        EventShortDto eventShortDto = eventMapper.mapToEventShortDto(eventToReturn);
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
//        int page = from / size;
//        Pageable pageable = PageRequest.of(page, size);
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> eventsList = eventRepository.findAllByInitiatorId(userId, pageable);
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




//    public List<EventShortDto> getEvents(HttpServletRequest request, String text, int[] categories, Boolean paid,
//                                                 String rangeStart, String rangeEnd,
//                                                 boolean onlyAvailable, String sort, Integer from, Integer size) {
//        //отправка информации на сервер статистики
//        //sendStatistic(request); // TODO: 02.10.2022 отправка на сервер статистики
//        LocalDateTime dateStart = rangeStart == null || rangeStart.isEmpty() ?
//                LocalDateTime.now() :
//                LocalDateTime.parse(rangeStart, formatter);
//        LocalDateTime dateEnd = rangeEnd == null || rangeEnd.isEmpty() ?
//                null : LocalDateTime.parse(rangeEnd, formatter);
//
//        Set<Integer> categorySet = categories == null ?
//                new HashSet<>() : Arrays.stream(categories).boxed().collect(Collectors.toSet());
//
//        List<Event> events = eventRepository.findEventsByParams(
//                text != null && !text.isEmpty(), "%" + text + "%", !categorySet.isEmpty(),
//                categorySet, paid != null, paid,
//                dateEnd == null, dateStart, dateEnd == null ? dateStart : dateEnd,
//                onlyAvailable, PageRequest.of(from / size, size)
//        );
//
//        List<EventShortDto> eventShortDtos = eventMapper.mapAlltoShortDto(events);
//
//        switch (sort) {
//            case ("VIEWS"):
//                eventShortDtos.sort(Comparator.comparing(EventShortDto::getViews));
//                break;
//            case ("EVENT_DATE"):
//                eventShortDtos.sort(Comparator.comparing(EventShortDto::getEventDate));
//        }
//        return eventShortDtos;
//    }

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

//    public List<EventShortDto> getEvents(String text,
//                                         Integer categoryId,
//                                         Boolean paid,
//                                         String rangeStart,
//                                         String rangeEnd,
//                                         Boolean onlyAvailable,
//                                         String sort,
//                                         Integer from,
//                                         Integer size,
//                                         HttpServletRequest request) {

//        Stats stats = new Stats(0,
//                "explore-with-me",
//                request.getRequestURI(),
//                request.getRemoteAddr(),
//                LocalDateTime.now());
//
//
//        client.saveRequest(stats);

//        return eventRepository.findAll().stream()
//                .map(EventMapper::mapToEventShortDto)
//                .collect(Collectors.toList());
//    }


//    public List<EventFullDto> getEventsAdmin(int[] users,
//                                             String[] states, int[] categories, String rangeStart, String rangeEnd, int from, int size) {
//        return new ArrayList<>(); // TODO: 02.10.2022
//    }

    public EventFullDto updateEvent(int eventId, AdminUpdateEventRequest adminUpdateEventRequest) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new UserCastomException("событие не найдено"));
//
//            if (!userId.equals(event.getInitiator().getId())) {
//                String message = "Only initiator can update event.";
//                log.warn("ForbiddenOperationException at EventServiceImpl.updateEvent: {}", message);
//                throw new ForbiddenOperationException(message);
//            }
        EventStatus state = event.getStatus();
        if (!EventStatus.CANCELED.equals(state) && !EventStatus.PENDING.equals(state)) {
            String message = "Только события со статусом pending или canceled может быть изменено";
            log.warn("ForbiddenOperationException at EventServiceImpl.updateEvent: {}", message);
            throw new OperationException(message);
        }
        if (adminUpdateEventRequest.getTitle() != null) event.setTitle(adminUpdateEventRequest.getTitle());
        if (adminUpdateEventRequest.getAnnotation() != null)
            event.setAnnotation(adminUpdateEventRequest.getAnnotation());
        if (adminUpdateEventRequest.getDescription() != null)
            event.setDescription(adminUpdateEventRequest.getDescription());
        if (adminUpdateEventRequest.getEventDate() != null) event.setEventDate(adminUpdateEventRequest.getEventDate());
        if (adminUpdateEventRequest.getPaid() != null) event.setPaid(adminUpdateEventRequest.getPaid());
        if (adminUpdateEventRequest.getCategory() != null) {
            event.setCategory(new Category(adminUpdateEventRequest.getCategory(), null));
        }
        if (adminUpdateEventRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(adminUpdateEventRequest.getParticipantLimit());
        }
        event.setStatus(EventStatus.PENDING); //PENDING
        Event updatedEvent = eventRepository.save(event);
        log.info("EventServiceImpl.updateEvent: event {} successfully updated", event.getEventId());
        return eventMapper.mapToFullEventDto(updatedEvent);
    }

    public EventFullDto publishEvent(Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new UserCastomException("событие не найдено"));

        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            String message = "Только события которые начинаются на час больше текущенго времени могут быть опубликованы.";
            log.warn("ForbiddenOperationException at EventServiceImpl.publishEvent: {}", message);
            throw new OperationException(message);
        }
        if (!EventStatus.PENDING.equals(event.getStatus())) {
            String message = "Только события в статусе pending  могут быть опубликованы.";
            log.warn("ForbiddenOperationException at EventServiceImpl.publishEvent: {}", message);
            throw new OperationException(message);
        }
        event.setStatus(EventStatus.PUBLISHED);
        Event publishedEvent = eventRepository.save(event);
        log.info("EventServiceImpl.publishEvent: event {} successfully published", event.getEventId());
        return eventMapper.mapToFullEventDto(publishedEvent);
    }

    public EventFullDto rejectEvent(int eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new UserCastomException("событие не найдено"));
        if (!EventStatus.PENDING.equals(event.getStatus())) {
            String message = "Только события в статусе pending  могут быть опубликованы.";
            log.warn("ForbiddenOperationException at EventServiceImpl.rejectEvent: {}", message);
            throw new OperationException(message);
        }
        event.setStatus(EventStatus.CANCELED);
        Event rejectedEvent = eventRepository.save(event);
        log.info("EventServiceImpl.rejectEvent: event {} successfully rejected", event.getEventId());
        return eventMapper.mapToFullEventDto(rejectedEvent);
    }

    public List<EventShortDto> getEventsByIds(List<Integer> ids) {
        return eventRepository.findAllById(ids)
                .stream()
                .map(eventMapper::mapToEventShortDto)
                .collect(Collectors.toList());
    }


    public Collection<EventFullDto> getEventByAdmin(List<Long> users, List<String> statesStr, List<Long> categories, String rangeStart, String rangeEnd, int from, int size) {
        LocalDateTime start;
        LocalDateTime end;
        boolean isUsers = users.isEmpty() ? false : true;
        boolean isCat = categories.isEmpty() ? false : true;
        boolean isState = statesStr.isEmpty() ? false : true;

        if (rangeStart.isEmpty()) {
            start = LocalDateTime.now();
        } else {
            start = LocalDateTime.parse(URLDecoder.decode(rangeStart, StandardCharsets.UTF_8), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        if (rangeEnd.isEmpty()) {
            end = LocalDateTime.now().minusYears(100);
        } else {
            end = LocalDateTime.parse(URLDecoder.decode(rangeEnd, StandardCharsets.UTF_8), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        return eventRepository.findByAdmin(isUsers, users, isState, statesStr, isCat, categories, start, end, PageRequest.of(from, size))
                .stream()
                .map(eventMapper::mapToFullEventDto)
                .collect(Collectors.toList());
    }

    public Collection<EventShortDto> getFilteredEvents(String text,
                                               List<Long> categories,
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
            start = LocalDateTime.parse(URLDecoder.decode(rangeStart, StandardCharsets.UTF_8), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        if (rangeEnd.isEmpty()) {
            end = LocalDateTime.now().minusYears(100);
        } else {
            end = LocalDateTime.parse(URLDecoder.decode(rangeEnd, StandardCharsets.UTF_8), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        if (text != null) {
            text.toLowerCase();
        }
        var events = eventRepository.find(text, isCategories, categories, paid, start, end, onlyAvailable, PageRequest.of(from, size));

        if (sort != null) {
            if (sort == EventSortValues.EVENT_DATE) {
                events.stream().sorted(Comparator.comparing(Event::getEventDate));
            }
            if (sort == EventSortValues.VIEWS) {
                events.stream().sorted(Comparator.comparing(Event::getEventDate));
            }
        }


        return events.stream()
                .map(event -> eventMapper.mapToEventShortDto(event))
                .collect(Collectors.toList());
    }
}
