package ru.practicum.app.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllById(int[] array, Pageable page);

    List<User> findUsersByIdIn(Set<Integer> ids);
}
