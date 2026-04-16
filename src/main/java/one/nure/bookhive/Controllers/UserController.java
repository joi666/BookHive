package one.nure.bookhive.Controllers;

import one.nure.bookhive.Models.DataTransferObjects.UserDTO;
import one.nure.bookhive.Models.User;
import one.nure.bookhive.Services.UserService;
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
    public UserDTO register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping(path = "/login")
    public UserDTO login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userService.loginUser(email, password);
    }

    @GetMapping(path = "/get/{userId}")
    public UserDTO getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @PutMapping(path = "/update/{userId}")
    public UserDTO updateUser (@PathVariable UUID userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping(path = "/delete/{userId}")
    public void deleteUser (@PathVariable UUID userId) {
        userService.deleteUser(userId);
    }
}
