package ru.practicum.app.user;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserDto create(UserDto userDto);

     void delete(int userId) throws EntryNotFoundException;

//    List<UserDto> getUser(List<String> ids, Integer from, Integer size);

    List<UserDto> getUsersById(Set<Integer> ids);
}
