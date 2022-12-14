package ru.practicum.app.repository.compilation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.app.model.compilation.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    @Query("SELECT c FROM Compilation AS c " +
            "WHERE :pinned IS NULL OR c.pinned = :pinned")
    List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}
