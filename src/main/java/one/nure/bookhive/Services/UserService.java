package one.nure.bookhive.Services;

import one.nure.bookhive.Models.User;
import one.nure.bookhive.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final Pattern EMAIL_PATTERN = Pattern.compile("\\w+[@]\\w+[.]\\w{1,3}");

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser (User user) {
        try {;
            Matcher emailMatcher = EMAIL_PATTERN.matcher(user.getEmail());
            if (emailMatcher.find()) {
                User newUser = new User();

                newUser.setEmail(user.getEmail());
                newUser.setPassword(user.getPassword());
                newUser.setUser_name(user.getUser_name());
                newUser.setUser_lastname(user.getUser_lastname());
                newUser.setAccount_creation_date(LocalDate.now());

                return userRepository.save(newUser);
            }
            else {
                throw new IllegalArgumentException("Invalid email format");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User loginUser (String email, String password) {
        try {
            Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
            if (emailMatcher.find()) {
                return userRepository.findUserByEmailAndPassword(email, password);
            }
            else {
                throw new IllegalArgumentException("Invalid email format");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User updateUser (UUID userId, User user) {
        User existingUser = userRepository.findById(user.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("User not found with id: " + user.getUserId()));

        existingUser.setUser_lastname(user.getUser_lastname());
        existingUser.setUser_name(user.getUser_name());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);
    }

    public void deleteUser (UUID userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
    }
}
