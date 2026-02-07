package one.nure.bookhive.Services;

import one.nure.bookhive.Models.DataTransferObjects.AuthorShortDTO;
import one.nure.bookhive.Models.DataTransferObjects.BookDTO;
import one.nure.bookhive.Models.DataTransferObjects.UserDTO;
import one.nure.bookhive.Models.Genre;
import one.nure.bookhive.Models.User;
import one.nure.bookhive.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final Pattern EMAIL_PATTERN = Pattern.compile("\\w+@\\w+[.]\\w{1,3}");

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user details by email (used as username in authentication).
     * May be fixed in the future (implement authentication by username or email).
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

    public UserDTO registerUser (User user) {
        try {
            Matcher emailMatcher = EMAIL_PATTERN.matcher(user.getEmail());
            if (emailMatcher.find()) {
                User newUser = new User();

                newUser.setEmail(user.getEmail());
                newUser.setPassword(user.getPassword());
                newUser.setUserName(user.getUserName());
                newUser.setUserLastname(user.getUserLastname());
                newUser.setAccount_creation_date(LocalDate.now());

                return convertToUserDTO(userRepository.save(newUser));
            }
            else {
                throw new IllegalArgumentException("Invalid email format");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserDTO loginUser (String email, String password) {
        try {
            Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
            if (emailMatcher.find()) {
                return convertToUserDTO(userRepository.findUserByEmailAndPassword(email, password));
            }
            else {
                throw new IllegalArgumentException("Invalid email format");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserDTO updateUser (UUID userId, User user) {
        User existingUser = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found with id: " + user.getUserId()));

        existingUser.setUserName(user.getUserName());
        existingUser.setUserLastname(user.getUserLastname());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return convertToUserDTO(userRepository.save(existingUser));
    }

    public void deleteUser (UUID userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
    }

    public static UserDTO convertToUserDTO(User user) {
        UserDTO dto = new UserDTO();

        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setAccountCreationDate(user.getAccount_creation_date());
        dto.setUserName(user.getUserName());
        dto.setUserLastname(user.getUserLastname());

        return dto;
    }
}
