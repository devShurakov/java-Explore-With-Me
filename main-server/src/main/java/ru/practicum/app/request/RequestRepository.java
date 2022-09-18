package ru.practicum.app.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.app.request.model.Request;

public interface RequestRepository extends JpaRepository<Request, Integer> {
}
