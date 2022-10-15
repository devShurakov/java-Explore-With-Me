package ru.practicum.app.repository.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.app.model.compilation.CompilationEvent;

import java.util.List;

public interface CompilationEventRepository extends JpaRepository<CompilationEvent, Integer> {
    @Query("SELECT ce.compilation FROM CompilationEvent AS ce " +
            "WHERE ce.compilation = :compId")
    List<Integer> findCompilationEventIds(Integer compId);

    @Transactional
    void deleteByCompilationAndEvent(Integer compId, Integer eventId);
}
