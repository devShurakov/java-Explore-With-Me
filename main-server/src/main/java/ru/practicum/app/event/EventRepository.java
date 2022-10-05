package ru.practicum.app.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Integer> {
//    List<Event> findAllByInitiator_Id(Long userId, Pageable pageable);

@Query(value = "SELECT e.* FROM events AS e " +
        "LEFT JOIN (" +
        "   SELECT COUNT(r.id) AS COUNT, event_id FROM requests AS r " +
        "   WHERE r.requests_status = 3 GROUP BY event_id" +
        ") AS rcount ON rcount.event_id = e.id " +
        "WHERE (false = :searchByText OR UPPER(e.annotation) like :text OR UPPER(e.description) like :text) " +
        "AND (false = :searchByCategory OR e.category_id IN :categoryIds) " +
        "AND (false = :searchByIsPaid OR e.paid = :isPaid) " +
        "AND (false = :searchByOneDate OR e.event_date > :rangeStart) " +
        "AND (true = :searchByOneDate OR e.event_date between :rangeStart and :rangeEnd) " +
        "AND (false = :searchByOnlyAvailable OR e.participant_limit = 0 OR e.participant_limit > rcount.COUNT)",
        nativeQuery = true)
List<Event> findEventsByParams(
        @Param("searchByText")boolean searchByText,
        @Param("text")String text,
        @Param("searchByCategory")boolean searchByCategory,
        @Param("categoryIds") Set<Integer> categoryIds,
        @Param("searchByIsPaid")boolean searchByIsPaid,
        @Param("isPaid")Boolean isPaid,
        @Param("searchByOneDate")boolean searchByOneDate,
        @Param("rangeStart")LocalDateTime rangeStart,
        @Param("rangeEnd")LocalDateTime rangeEnd,
        @Param("searchByOnlyAvailable")boolean searchByOnlyAvailable,
        Pageable pageable
);

    List<Event> findAllByInitiatorId(Integer userId, Pageable pageable);
}
