package ru.practicum.app.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Transactional
    @Query("select r from Request r where r.requester.id = ?1")
    List<Request> findAllByRequester(Integer userId);


    @Transactional
    @Modifying
    @Query("update Request r set r.status = ?1 where r.id = ?2")

    void setStateById(String status, Integer reqId);

    @Query("select r from Request r left join Event e on r.event.eventId = e.eventId " +
            "where e.initiator.id = ?1 and e.eventId = ?2")
    List<Request> getRequestByUser(Integer userId, Integer eventId);
}
