package ru.practicum.app.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public void delete(int userId) throws EntryNotFoundException {
        try {
            userRepository.deleteById(userId);
            log.info("Пользователь удален");
        } catch (EmptyResultDataAccessException e) {
            throw new EntryNotFoundException("Несуществующий пользователь с id: " + userId);
        }

    }

    @Override
    public List<UserDto> getUser(List<String> ids, Integer from, Integer size) {
        List<Integer> ids2 = new ArrayList<>();
        if (!isNumber(ids)) {
            throw new UserCastomException("не может быть преобразовано в число");
        }
        for (String x : ids) {
            ids2.add(Integer.valueOf(x));
        }

        if (ids != null || !ids.isEmpty()) {
            log.info("Пользователь  получен");
            return userRepository.findAllById(ids2).stream()
                    .map(UserMapper::mapToUserDto)
                    .collect(Collectors.toList());
        } else if (ids == null || ids.isEmpty() || ids.isEmpty() && from == null) {
            log.info("Пользователь  получен");
            return userRepository.findAll().stream()
                    .map(UserMapper::mapToUserDto)
                    .collect(Collectors.toList());
        } else {
            int page = from / size;
            Pageable pageable = PageRequest.of(page, size);
            log.info("Пользователь  получен");
            return userRepository.findAll(pageable).stream()
                    .map(UserMapper::mapToUserDto)
                    .collect(Collectors.toList());
        }
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
