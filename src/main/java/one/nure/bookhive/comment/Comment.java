package one.nure.bookhive.comment;

import jakarta.persistence.*;
import lombok.*;
import one.nure.bookhive.book.Book;
import one.nure.bookhive.user.User;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String comment_body;
    private Integer comment_likes;
    private LocalDate comment_date;
}
