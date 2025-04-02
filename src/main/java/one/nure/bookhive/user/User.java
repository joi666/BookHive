package one.nure.bookhive.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID user_id;

    private String user_password;
    private Date account_creation_date;
    private String user_name;
    private String user_lastname;

    public User() {
    }

    public User(UUID user_id, String user_password, Date account_creation_date, String user_name, String user_lastname) {
        this.user_id = user_id;
        this.user_password = user_password;
        this.account_creation_date = account_creation_date;
        this.user_name = user_name;
        this.user_lastname = user_lastname;
    }

    public User(String user_password, Date account_creation_date, String user_name, String user_lastname) {
        this.user_password = user_password;
        this.account_creation_date = account_creation_date;
        this.user_name = user_name;
        this.user_lastname = user_lastname;
    }
}
