package ru.practicum.app.user;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

     void delete(int userId) throws EntryNotFoundException;

    List<UserDto> getUser(List<String> ids, Integer from, Integer size);
}
