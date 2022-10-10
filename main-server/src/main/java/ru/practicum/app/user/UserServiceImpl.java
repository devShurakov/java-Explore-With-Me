package ru.practicum.app.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.app.exception.EntryNotFoundException;
import ru.practicum.app.exception.UserCastomException;

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
    public UserDto create(UserDto userDto) {
        if (userDto.getName() == null || userDto.getEmail() == null) {
            throw new UserCastomException("Неверные данные");
        }

        User savedUser = userRepository.save(userMapper.mapToUser(userDto));
        log.info("пользователь создан");
        return UserMapper.mapToUserDto(savedUser);
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

    public List<UserDto> getAllUsers(List<Integer> ids, int from, int size) {
        List<User> userCollection = userRepository.findAllById(ids, PageRequest.of(from, size));


        return userMapper.maptoAllUserDto(userCollection);
    }

//    public List<UserDto> getAllWithPagable(Integer from, Integer size) {
//        Pageable page = PageRequest.of(from, size);
//        Page<User> userCollection = userRepository.findAll(page);
//
//        return userMapper.maptoAllUserDto(userCollection);
//    }

//    public List<UserDto> getAllWithPagable(int from, int size) {
//        Pageable page = PageRequest.of(from, size);
//        Page<User> userCollection = userRepository.findAll(page);
//
//        return userMapper.maptoAllUserDto(userCollection);
//    }

    @Override
    public List<UserDto> getUsersById(Set<Integer> ids) {
        return userRepository.findUsersByIdIn(ids)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public boolean isNumber(List<String> ids) {
        for (String x : ids) {
            if (x == null || x.isEmpty()) return false;
            for (int i = 0; i < x.length(); i++) {
                if (!Character.isDigit(x.charAt(i))) return false;
            }
        }
        return true;
    }


}
