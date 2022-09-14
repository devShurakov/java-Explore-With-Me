package user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import user.dto.UserDto;
import user.service.UserServiceImpl;

import java.util.List;


//@RestController
@Controller
@RequestMapping(value = "/admin/users")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }


    @GetMapping(value = "/{userId}")
    public List<UserDto> getUser(@RequestParam("ids") long[] ids,
                                @RequestParam(defaultValue = "0") int from,
                                @RequestParam(defaultValue = "10") int size) {
        return userService.getUser(ids, from, size);
    }

    @DeleteMapping(value = "/{userId}")
    public HttpStatus delete(@PathVariable int userId) {
        return userService.delete(userId);
    }

}

