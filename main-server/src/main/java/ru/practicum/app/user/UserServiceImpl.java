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
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public UserDto create(UserDto userDto) {
        if(userDto.getName() == null || userDto.getEmail() == null){
            throw new UserCastomException("Неверные данные");
        }

         User user = userMapper.mapToUser(userDto);
         User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);

//        if(userDto.getName() == null || userDto.getEmail() == null){
//            throw new UserCastomException("Неверные данные");
//        }
//        User user = userRepository.save(userMapper.mapToUser(userDto));
//        log.info("Пользователь создан");
//        UserDto returnUserDto = userMapper.mapToUserDto(user);
//        return returnUserDto;
    }

    @Override
    public HttpStatus delete(int userId) {
        log.info("Пользователь удален");
        userRepository.deleteById(userId);
        return HttpStatus.OK;
    }

    int a = 1;
    @Override
    public List<UserDto> getUser(List<Integer> ids, Integer from, Integer size) {

        if (!ids.isEmpty()) {
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

//        checkIds(ids);
//        a++;
//        if (ids == null && ids.length==0){
//          throw new UserCastomException2("Неправильный формат ввода");
//        }
////            long[] array = Arrays.stream(ids).mapToLong(Long::parseLong).toArray();
//        int[] array = Arrays.stream(ids).mapToInt(Integer::parseInt).toArray();
//        List<UserDto> returnList = new ArrayList<>();
//
//        if (size != null) {
//            Pageable page = PageRequest.of(from, size);
//            returnList = userMapper.maptoAllUserDto(userRepository.findAllById(array,page));
//        } else {
//            for (int x : array) {
//                returnList.add(userMapper.mapToUserDto(userRepository.findById(x).orElseThrow(() ->
//                        new UserCastomException("Пользователь не найден"))));
//            }
//        }
//        return returnList;
    }

//    private void checkIds(String[] ids) {
//        boolean exceptionFrom =false;
//        try {
//            for (String x : ids) {
//                Long.parseLong(x);
//            }
//        } catch (NumberFormatException e) {
//            exceptionFrom = true;
//            throw new UserCastomException2("Input String cannot be parsed to Integer.");
//        }
//
//        if (exceptionFrom == true){
//            throw new UserCastomException2("Input String cannot be parsed to Integer.");
//        }
//    }
}
