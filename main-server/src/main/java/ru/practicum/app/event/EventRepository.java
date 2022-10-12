package ru.practicum.app.event;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Transactional
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

//    @Transactional
//    @Query(value = "SELECT * FROM events e " +
//            "WHERE " +
//            "(e.event_status = 'PUBLISHED')",
//            nativeQuery = true)

//    @Query("SELECT i FROM Item i " +
//            "WHERE (UPPER(i.name)) LIKE UPPER(CONCAT('%', ?1, '%')) " +
//            " OR (UPPER(i.description)) LIKE UPPER(CONCAT('%', ?1, '%'))" +
//            " AND i.available = true"
//    )

//    @Query(value = "SELECT * FROM events e " +
//            "WHERE " +
//            "(e.event_status = 'PUBLISHED') and " +
//            "((:isCategories = false) or (e.category_id in :categories) ) and " +
//            "(e.event_date between :rangeStart and :rangeEnd) and" +
//            "(e.participant_limit > confirmed_requests or :onlyAvailable is null ) and " +
//            "((upper(e.annotation) like UPPER(%:text%) or lower(e.description) like UPPER(%:text%)) or :text is null) and " +
//            "(e.paid = :paid or :paid is null)",
//            nativeQuery = true)
@Query(value = "SELECT * FROM events e " +
        "WHERE " +
        "(e.event_status = 'PUBLISHED') and " +
        "((:isCategories = false) or (e.category_id in :categories) ) and " +
        "(e.event_date between :rangeStart and :rangeEnd) and" +
        "(e.participant_limit > confirmed_requests or :onlyAvailable is null ) and " +
        "(upper(e.annotation) like upper(CONCAT('%',:text,'%')) or upper(e.description) like upper(CONCAT('%',:text,'%')) or :text is null) and " +
        "(e.paid = :paid or :paid is null)",
        nativeQuery = true)
    List<Event> find(String text,
                     Boolean isCategories,
                     List<Integer> categories,
                     Boolean paid,
                     LocalDateTime rangeStart,
                     LocalDateTime rangeEnd,
                     Boolean onlyAvailable,
                     PageRequest pageRequest);

    @Query(value = "SELECT * FROM events e " +
            "WHERE " +
            "(e.event_id = :eventId) and " +
            "(e.initiator_id = :userId)",
            nativeQuery = true)
    Event findByIdAndInitiator_Id(Integer eventId, Integer userId);
}

