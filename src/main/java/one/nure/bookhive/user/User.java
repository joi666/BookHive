package one.nure.bookhive.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;

    private String email;
    @Column(name = "user_password")
    private String password;
    private LocalDate account_creation_date;
    private String user_name;
    private String user_lastname;
}
