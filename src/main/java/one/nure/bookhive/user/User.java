package one.nure.bookhive.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String user_id;
    public String user_password;
    public Date account_creation_date;
    public String user_name;
    public String user_lastname;

    public User() {
    }

    public User(String user_id, String user_password, Date account_creation_date, String user_name, String user_lastname) {
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
