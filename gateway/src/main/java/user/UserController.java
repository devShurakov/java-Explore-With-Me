package user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import user.user.UserDto;


@Controller
@Validated
@RequestMapping(value = "/admin/users")
public class UserController {

    private final UserClient userClient;

    @Autowired
    public UserController(UserClient userClient) {

        this.userClient = userClient;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UserDto userDto)  {
        return userClient.create(userDto);
    }

}
