package ru.practicum.app.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query("select r from Request r where r.requester.id = ?1")
    List<Request> findAllByRequester(Integer userId);
}
