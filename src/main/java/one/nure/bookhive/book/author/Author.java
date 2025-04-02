package one.nure.bookhive.book.author;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import one.nure.bookhive.book.Book;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long author_id;

    private String author_fullname;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    public Author() {
    }

    public Author(Long author_id, String author_fullname) {
        this.author_id = author_id;
        this.author_fullname = author_fullname;
    }

    public Author(String author_fullname) {
        this.author_fullname = author_fullname;
    }
}
