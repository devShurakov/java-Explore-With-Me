//package ru.practicum.app.user;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//import javax.validation.constraints.NotNull;
//
//
//@RestController
//@RequestMapping
//@Validated
//public class UserController {
//
//    private final UserServiceImpl userService;
//
//    @Autowired
//    public UserController(UserServiceImpl userService) {
//        this.userService = userService;
//    }
//
//
//    /***
//     *  Admin: Пользователи. API для работы с пользователями
//     *  - добавление нового польщователя
//     *  - получение списка пользователей
//     *  - удаление пользователя
//     */
////    @PostMapping(value = "/admin/users")
////    public UserDto create(@RequestBody UserDto userDto) {
////        return userService.create(userDto);
////    }
////
////
////    @GetMapping(value = "/admin/users")
////    public List<UserDto> getUser(@RequestParam(required = false) List<Integer> ids,
////                                 @RequestParam(defaultValue = "0") Integer from,
////                                 @RequestParam(defaultValue = "10") Integer size) {
//////        if (ids != null && !ids.isEmpty()) return userService.getUsersById(ids);
////            return userService.getAllUsers(ids,from, size);
////    }
//
////    @GetMapping(value = "/admin/users")
////    public List<UserDto> getUser(@RequestParam(value = "ids", defaultValue = "0") Set<Integer> ids,
////                                 @RequestParam(required = false) Integer from,
////                                 @RequestParam(required = false) Integer size) {
////        if (ids != null && !ids.isEmpty()) return userService.getUsersById(ids);
////        return userService.getUser(from, size);
////    }
////    @GetMapping
////    public Collection<UserDto> getAllUsers(@RequestParam(required = false) List<Long> ids,
////                                           @RequestParam(defaultValue = "0") int from,
////                                           @RequestParam(defaultValue = "10") int size) {
////        return userService.getAllUsers(ids, from, size)
////                .stream()
////                .map(user -> UserMapper.toUserDto(user))
////                .collect(Collectors.toList());
////    }
//
//
////    @DeleteMapping(value = "admin/users/{userId}")
////    public void delete(@PathVariable @NotNull int userId) throws EntryNotFoundException {
////        userService.delete(userId);
////    }
//
//
//
//
//}
//
