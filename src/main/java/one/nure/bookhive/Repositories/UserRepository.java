package one.nure.bookhive.Repositories;

import one.nure.bookhive.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findUserByEmailAndPassword(String email, String password);
    User findUserByEmail(String email);
    boolean existsByEmail(String email);
}
