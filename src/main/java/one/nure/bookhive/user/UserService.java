package one.nure.bookhive.user;

import one.nure.bookhive.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserService userService) {
        this.userRepository = userService.userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser (User user) {
        return userRepository.save(user);
    }

    public User updateUser (User user) {
        User existingUser = userRepository.findById(user.getUser_id()).orElseThrow(() ->
                new IllegalArgumentException("User not found with id: " + user.getUser_id()));
        //insert new data to existing book
        return userRepository.save(existingUser);
    }

    public void deleteUser (String user_id) {
        if (!userRepository.existsById(user_id)) {
            throw new IllegalArgumentException("User not found with id: " + user_id);
        }
        userRepository.deleteById(user_id);
    }
}
