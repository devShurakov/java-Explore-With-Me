package ru.practicum.app.service.user;

import ru.practicum.app.exception.EntryNotFoundException;
import ru.practicum.app.model.user.NewUserRequest;
import ru.practicum.app.dto.user.UserDto;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserDto create(NewUserRequest newUserRequest);

    void delete(int userId) throws EntryNotFoundException;

    List<UserDto> getAllUsers(List<Integer> ids, int from, int size);

    List<UserDto> getUsersById(Set<Integer> ids);
}
