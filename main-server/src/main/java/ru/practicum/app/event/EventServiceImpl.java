package ru.practicum.app.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.app.category.Category;
import ru.practicum.app.category.CategoryRepository;
import ru.practicum.app.event.dto.EventFullDto;
import ru.practicum.app.event.dto.EventShortDto;
import ru.practicum.app.event.dto.NewEventDto;
import ru.practicum.app.event.dto.UpdateEventRequest;
import ru.practicum.app.event.model.Event;
import ru.practicum.app.user.User;
import ru.practicum.app.user.UserCastomException;
import ru.practicum.app.user.UserRepository;

@Service
@Slf4j
public class EventServiceImpl {

    private final EventRepository evenRepository;
    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventServiceImpl(EventRepository evenRepository,
                            UserRepository userRepository,
                            EventMapper eventMapper,
                            CategoryRepository categoryRepository) {

        this.evenRepository = evenRepository;
        this.eventMapper = eventMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // Добавление нового события
    public EventShortDto create(int userId, NewEventDto newEventDto) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserCastomException("пользователь не найден"));
        Event event = evenRepository.save(eventMapper.mapToEvent(user, newEventDto));
        EventShortDto eventShortDto = eventMapper.mapToEventShortDto(event);
        log.info("создано новое событие {}", newEventDto.getTitle());
        return eventShortDto;
    }

    //изменение события добавленного текущим пользователем
    public UpdateEventRequest update(int userId, UpdateEventRequest updateEventRequest) {
        Event event = evenRepository
                .findById(updateEventRequest.getId())
                .orElseThrow(() -> new UserCastomException("событие не найдено"));

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

        evenRepository.save(event);

        UpdateEventRequest updatedEvent = eventMapper.mapToUpdateEventRequest(event);

        log.info("Событие с id: {} обновлено", updateEventRequest.getId());
        return updatedEvent;
    }

    // Отмена события добавленного пользователем
    public EventFullDto cancelEvent(int userId, int eventId) {
        Event event = evenRepository
                .findById(eventId)
                .orElseThrow(() -> new UserCastomException("событие не найдено"));
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserCastomException("пользователь не найден"));

        if (event.getInitiator().getId().intValue() != user.getId()) {
            throw new UserCastomException("Пользователь не является создателем события");
        }
        log.info("событие {} отменено", event.getTitle());
        return new EventFullDto();
    }

}
