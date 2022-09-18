package ru.practicum.app.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    public HttpStatus delete(int userId) {
        userRepository.deleteById(userId);
        log.info("Пользователь удален");
        return HttpStatus.OK;
    }

    @Override
    public List<UserDto> getUser(List<Integer> ids, Integer from, Integer size) {

        if (ids != null && !ids.isEmpty()) {
            return userRepository.findAllById(ids).stream()
                    .map(UserMapper::mapToUserDto)
                    .collect(Collectors.toList());
        } else {
            int page = from / size;
            Pageable pageable = PageRequest.of(page, size);
            return userRepository.findAll(pageable).stream()
                    .map(UserMapper::mapToUserDto)
                    .collect(Collectors.toList());
        }
    }
}
