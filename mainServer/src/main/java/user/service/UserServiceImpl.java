package user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import user.repository.UserRepository;
import user.dto.UserDto;
import user.dto.UserMapper;
import user.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {

        this.userRepository = userRepository;

        this.userMapper = userMapper;
    }

    public UserDto create(UserDto userDto) {
        User user = userRepository.save(userMapper.mapToUser(userDto));
        log.info("User created");
        return userMapper.mapToUserDto(user);
    }

    public HttpStatus delete(int userId) {
        log.info("User was deleted");
        userRepository.deleteById((long) userId);
        return HttpStatus.OK;
    }

    public List<UserDto> getUser(long[] ids, int from, int size) {
//    ArrayList<User> UserList = Arrays.stream(ids).forEach(i ->
//        userRepository.findById(i).orElseThrow(() -> {
//        log.warn("Entity not found");
//        return new UserNotFoundException(String.format("Entities with id %d not found", ((int) i)));
//    }));
//        return userMapper.maptoAllUserDto(UserList);
        return new ArrayList<>();
    }

}
