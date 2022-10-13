package ru.practicum.app.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.app.dto.user.UserDto;
import ru.practicum.app.exception.EntryNotFoundException;
import ru.practicum.app.exception.UserCastomException;
import ru.practicum.app.mapper.user.UserMapper;
import ru.practicum.app.model.user.NewUserRequest;
import ru.practicum.app.model.user.User;
import ru.practicum.app.repository.user.UserRepository;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public UserDto create(NewUserRequest newUserRequest) {
        if (newUserRequest.getName() == null || newUserRequest.getEmail() == null) {
            throw new UserCastomException("Неверные данные");
        }

        User savedUser = userRepository.save(userMapper.mapFromNewUsertoUser(newUserRequest));
        log.info("пользователь создан");
        return userMapper.mapToUserDto(savedUser);
    }

    @Override
    public void delete(int userId) throws EntryNotFoundException {
        try {
            userRepository.deleteById(userId);
            log.info("Пользователь удален");
        } catch (EmptyResultDataAccessException e) {
            throw new EntryNotFoundException("Несуществующий пользователь с id: " + userId);
        }

    }

    @Override
    public List<UserDto> getAllUsers(List<Integer> ids, int from, int size) {
        List<User> userCollection = userRepository.findAllById(ids, PageRequest.of(from, size));
        log.info("пользователи получены");
        return userMapper.maptoAllUserDto(userCollection);
    }

    @Override
    public List<UserDto> getUsersById(Set<Integer> ids) {
        log.info("пользователь получен");
        return userRepository.findUsersByIdIn(ids)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

}
