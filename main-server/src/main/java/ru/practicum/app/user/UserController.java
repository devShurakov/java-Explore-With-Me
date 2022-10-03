package ru.practicum.app.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.constraints.NotNull;



@RestController
@RequestMapping
@Validated
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    /***
     *  Admin: Пользователи. API для работы с пользователями
     *  - добавление нового польщователя
     *  - получение списка пользователей
     *  - удаление пользователя
     */
    @PostMapping(value = "/admin/users")
    public UserDto create(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }


    @GetMapping(value = "/admin/users")
    public List<UserDto> getUser(@RequestParam(value = "ids", defaultValue = "0") List<String> ids,
                                 @RequestParam(required = false) Integer from,
                                 @RequestParam(required = false) Integer size) {
            return userService.getUser(ids, from, size);
    }

    @DeleteMapping(value = "admin/users/{userId}")
    public void delete(@PathVariable @NotNull int userId) throws EntryNotFoundException {
        userService.delete(userId);
    }


}

