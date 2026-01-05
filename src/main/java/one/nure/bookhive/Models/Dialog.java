package one.nure.bookhive.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dialogs")
public class Dialog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "dialog_participants",
            joinColumns = @JoinColumn(name = "dialog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants;

    public Dialog(Set<User> participants) {
        this.participants = participants;
    }
}
