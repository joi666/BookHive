package one.nure.bookhive.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;
    private Instant created_at;
    private Boolean isRead;

    public Message(Dialog dialog, User user, String content) {
        this.dialog = dialog;
        this.user = user;
        this.content = content;
        this.created_at = Instant.now();
        this.isRead = false;
    }
}
