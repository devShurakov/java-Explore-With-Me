package ru.practicum.app.user;

import ru.practicum.app.exception.EntryNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserDto create(NewUserRequest newUserRequest);

    void delete(int userId) throws EntryNotFoundException;

    List<UserDto> getUsersById(Set<Integer> ids);
}
