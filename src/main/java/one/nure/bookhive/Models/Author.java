package one.nure.bookhive.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @Column(name = "author_fullname")
    private String authorFullname;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    public Author() {
    }

    public Author(Long author_id, String author_fullname) {
        this.author_id = author_id;
        this.authorFullname = author_fullname;
    }

    public Author(String author_fullname) {
        this.authorFullname = author_fullname;
    }
}
