package ru.practicum.app.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByRequesterId(Integer userId);


    @Query("select r from Request r left join Event e on r.event.eventId = e.eventId " +
            "where e.initiator.id = ?1 and e.eventId = ?2")
    List<Request> getRequestByUser(Integer userId, Integer eventId);
}
