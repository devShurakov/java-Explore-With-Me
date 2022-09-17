package ru.practicum.app.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.app.event.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
