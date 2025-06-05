package one.nure.bookhive.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PutMapping
    public User updateUser (@RequestBody User user) {
        return userService.updateUser(user);
    }

//    @GetMapping
//    public List<User> getUsers() {
//        return userService.getUsers();
//    }
//
//    @PostMapping
//    public User createUser (@RequestBody User user) {
//        return userService.createUser(user);
//    }
//
//    @DeleteMapping
//    public void deleteUser (@RequestParam UUID user_id) {
//        userService.deleteUser(user_id);
//    }
}
