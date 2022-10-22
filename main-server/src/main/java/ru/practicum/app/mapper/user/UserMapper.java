package ru.practicum.app.mapper.user;

import ru.practicum.app.dto.user.UserDto;
import ru.practicum.app.model.user.NewUserRequest;
import ru.practicum.app.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public static List<UserDto> maptoAllUserDto(List<User> users) {
        List<UserDto> usersDtoList = new ArrayList<>();
        for (User user : users) {
            usersDtoList.add(mapToUserDto(user));
        }
        return usersDtoList;
    }

    public static User mapFromNewUsertoUser(NewUserRequest newUserRequest) {
        User user = new User();
        user.setName(newUserRequest.getName());
        user.setEmail(newUserRequest.getEmail());
        return user;
    }
}
