package one.nure.bookhive.comment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import one.nure.bookhive.book.Book;
import one.nure.bookhive.user.User;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private String comment_body;
    private Integer comment_likes;
    private Date comment_date;

    public Comment() {
    }

    public Comment(Long comment_id, User user, Book book, String comment_body, Integer comment_likes, Date comment_date) {
        this.comment_id = comment_id;
        this.user = user;
        this.book = book;
        this.comment_body = comment_body;
        this.comment_likes = comment_likes;
        this.comment_date = comment_date;
    }

    public Comment(User user, Book book, String comment_body, Integer comment_likes, Date comment_date) {
        this.user = user;
        this.book = book;
        this.comment_body = comment_body;
        this.comment_likes = comment_likes;
        this.comment_date = comment_date;
    }
}
