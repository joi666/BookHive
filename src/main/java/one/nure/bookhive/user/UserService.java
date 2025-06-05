package one.nure.bookhive.user;

import one.nure.bookhive.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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

    public User updateUser (User user) {
        User existingUser = userRepository.findById(user.getUser_id()).orElseThrow(() ->
                new IllegalArgumentException("User not found with id: " + user.getUser_id()));
        //insert new data to existing book
        return userRepository.save(existingUser);
    }

//    public List<User> getUsers() {
//        return userRepository.findAll();
//    }

//    public User createUser (User user) {
//        return userRepository.save(user);
//    }

//    public void deleteUser (UUID user_id) {
//        try {
//            userRepository.deleteById(user_id);
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("User not found with id: " + user_id);
//        }
//    }
}
