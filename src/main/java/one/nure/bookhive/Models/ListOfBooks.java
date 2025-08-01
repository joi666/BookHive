package one.nure.bookhive.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "lists")
public class ListOfBooks { //Needs to be renamed

    @EmbeddedId
    private ListId id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("book_id")
    @JoinColumn(name = "book_id")

    private Book book;
    private String status;
    private Integer pages_read;
    private Integer book_rating;

    public ListOfBooks() {
    }

    public ListOfBooks(User user, Book book, String status, Integer pages_read, Integer book_rating) {
        this.user = user;
        this.book = book;
        this.status = status;
        this.pages_read = pages_read;
        this.book_rating = book_rating;
    }
}
