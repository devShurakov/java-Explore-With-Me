package ru.practicum.app.user;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    List<User> findAllById(int[] array, Pageable page);

    @Query("FROM User WHERE id in :ids or :ids is null")
    Collection<User> findAllById(List<Integer> ids, PageRequest pageRequest);
    List<User> findUsersByIdIn(Set<Integer> ids);
}
