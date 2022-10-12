package ru.practicum.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.app.model.EndpointHit;
import ru.practicum.app.model.HitsRequest;

import java.time.LocalDateTime;

public interface StatisticRepository extends JpaRepository<EndpointHit, Long> {

    @Query(value = "SELECT count(s.id) as hits, s.app as app, s.uri as uri  FROM statistics s " +
            "WHERE " +
            " (s.timestamp between :start and :end)" +
            " and (s.uri = :uri)  group by s.app, s.uri",
            nativeQuery = true)
    HitsRequest find(LocalDateTime start, LocalDateTime end, String uri);

    @Query(value = "SELECT count(DISTINCT s.ip) as hits, s.app as app, s.uri as uri  FROM statistics s " +
            "WHERE " +
            " (s.timestamp between :start and :end)" +
            " and (s.uri = :uri) group by s.app, s.uri",
            nativeQuery = true)
    HitsRequest findDistinct(LocalDateTime start, LocalDateTime end, String uri);
}
