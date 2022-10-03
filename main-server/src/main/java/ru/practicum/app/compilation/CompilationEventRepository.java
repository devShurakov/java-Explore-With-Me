package ru.practicum.app.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CompilationEventRepository extends JpaRepository<CompilationEvent, Integer> {
    @Query("SELECT ce.compilation FROM CompilationEvent AS ce " +
            "WHERE ce.compilation = :compId")
    List<Long> findCompilationEventIds(Integer compId);

    @Transactional
    void deleteByCompilationAndEvent(Integer compId, Integer eventId);
}
