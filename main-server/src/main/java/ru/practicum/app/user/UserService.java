package ru.practicum.app.user;

import org.springframework.http.HttpStatus;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    HttpStatus delete(int userId);

    List<UserDto> getUser(List<Integer> ids, Integer from, Integer size);
}
