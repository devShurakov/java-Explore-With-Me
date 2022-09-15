package ru.practicum.app.event;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.practicum.app.event.dto.NewEventDto;

@Service
public class EventServiceImpl {
    public NewEventDto create(NewEventDto newEventDto) {
        return new NewEventDto();
    }
}
