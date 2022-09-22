package ru.practicum.app.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.app.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByInitiator_Id(Integer userId, Pageable pageable);

//    @Query(value = "SELECT e.event_id FROM events as e")
    List<Event>  customFinder(String text, List<String> categories, boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable, String sort, Integer from, Integer size);
}
