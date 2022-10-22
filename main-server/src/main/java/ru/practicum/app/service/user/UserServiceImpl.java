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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto create(NewUserRequest newUserRequest) {
        if (newUserRequest.getName().isBlank() || newUserRequest.getEmail().isBlank()) {
            throw new UserCastomException("Неверные данные");
        }
        checkEmail(newUserRequest.getEmail());
        User savedUser = userRepository.save(UserMapper.mapFromNewUsertoUser(newUserRequest));
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
    public List<UserDto> getAllUsers(List<Integer> ids, int from, int size) {
        List<User> userCollection = userRepository.findAllById(ids, PageRequest.of(from, size));
        log.info("пользователи получены");
        return UserMapper.maptoAllUserDto(userCollection);
    }

    @Override
    public List<UserDto> getUsersById(Set<Integer> ids) {
        log.info("пользователь получен");
        return userRepository.findUsersByIdIn(ids)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    private void checkEmail(String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException ex) {
            throw new UserCastomException("Email должен быть в формате Email");
        }
    }

}
