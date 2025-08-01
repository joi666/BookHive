package one.nure.bookhive.Repositories;

import one.nure.bookhive.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    public User findUserByEmailAndPassword(String email, String password);
}
