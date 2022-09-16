package ru.practicum.app.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.app.event.model.Event;
import ru.practicum.app.user.User;

public interface EventRepository extends JpaRepository<Event, Long> {
}
