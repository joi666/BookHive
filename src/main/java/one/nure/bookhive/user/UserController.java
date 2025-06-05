package one.nure.bookhive.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v01/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(path = "/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping(path = "/login")
    public User login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userService.loginUser(email, password);
    }

    @PutMapping(path = "/update/{userId}")
    public User updateUser (@PathVariable UUID userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping(path = "/delete/{userId}")
    public void deleteUser (@PathVariable UUID userId) {
        userService.deleteUser(userId);
    }
}
