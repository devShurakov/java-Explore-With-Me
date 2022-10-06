package ru.practicum.app.event;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

//    @Query(value = "SELECT e.* FROM events AS e " +
//            "LEFT JOIN (" +
//            "   SELECT COUNT(r.id) AS COUNT, event_id FROM requests AS r " +
//            "   WHERE r.requests_status = 3 GROUP BY event_id" +
//            ") AS rcount ON rcount.event_id = e.id " +
//            "WHERE (false = :searchByText OR UPPER(e.annotation) like :text OR UPPER(e.description) like :text) " +
//            "AND (false = :searchByCategory OR e.category_id IN :categoryIds) " +
//            "AND (false = :searchByIsPaid OR e.paid = :isPaid) " +
//            "AND (false = :searchByOneDate OR e.event_date > :rangeStart) " +
//            "AND (true = :searchByOneDate OR e.event_date between :rangeStart and :rangeEnd) " +
//            "AND (false = :searchByOnlyAvailable OR e.participant_limit = 0 OR e.participant_limit > rcount.COUNT)",
//            nativeQuery = true)
//    List<Event> findEventsByParams(
//            @Param("searchByText") boolean searchByText,
//            @Param("text") String text,
//            @Param("searchByCategory") boolean searchByCategory,
//            @Param("categoryIds") Set<Integer> categoryIds,//// TODO: 06.10.2022 set
//            @Param("searchByIsPaid") boolean searchByIsPaid,
//            @Param("isPaid") Boolean isPaid,
//            @Param("searchByOneDate") boolean searchByOneDate,
//            @Param("rangeStart") LocalDateTime rangeStart,
//            @Param("rangeEnd") LocalDateTime rangeEnd,
//            @Param("searchByOnlyAvailable") boolean searchByOnlyAvailable,
//            Pageable pageable
//    );

    @Query(value = "SELECT * FROM events e " +
            "WHERE " +
            "(e.event_status in :states or :isStates = false ) and " +
            "( e.category_id in :categories or :isCat = false) and " +
            "(e.initiator_id in :initiators or :isUsers = false) and" +
            "(e.event_date between :rangeStart and :rangeEnd) ",
            nativeQuery = true)
    Collection<Event> findByAdmin(boolean isUsers, List<Long> initiators, boolean isStates,
                                  List<String> states, boolean isCat,
                                  List<Long> categories,
                                  LocalDateTime rangeStart,
                                  LocalDateTime rangeEnd,
                                  PageRequest pageRequest);

    List<Event> findAllByInitiatorId(Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM events e " +
            "WHERE " +
            "(e.event_status = 'PUBLISHED') and " +
            "((:isCategories = false) or (e.category_id in :categories) ) and " +
            "(e.event_date between :rangeStart and :rangeEnd) and" +
            "(e.participant_limit > confirmed_requests or :onlyAvailable is null ) and " +
            "((lower(e.annotation) like %:text% or lower(e.description) like %:text%) or :text is null) and " +
            "(e.paid = :paid or :paid is null)",
            nativeQuery = true)
    Collection<Event> find(String text,
                           Boolean isCategories,
                           List<Long> categories,
                           Boolean paid,
                           LocalDateTime rangeStart,
                           LocalDateTime rangeEnd,
                           Boolean onlyAvailable,
                           PageRequest pageRequest);

}
