package one.nure.bookhive.Security;

import one.nure.bookhive.Models.User;
import one.nure.bookhive.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v01/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping(path = "/signin")
    public String authenticate(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @PostMapping(path = "/signup")
    @Transactional // Without transactional doesnt working (Why?)
    public String register(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Email already exists";
        }
        User newUser = new User(
                null,
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getAccount_creation_date(),
                user.getUser_name(),
                user.getUser_lastname()
        );
        userRepository.save(newUser);
        return "Registration was successful!";
    }
}
