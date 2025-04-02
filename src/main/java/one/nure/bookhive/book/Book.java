package one.nure.bookhive.book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import one.nure.bookhive.book.author.Author;
import one.nure.bookhive.book.genre.Genre;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id;

    @ManyToMany
    @JoinTable(name = "book_genres")
    private Set<Genre> genres;

    @ManyToMany
    @JoinTable(name = "book_authors")
    private Set<Author> authors;

    private String title;
    private Integer publishing_year;
    private Integer pages;
    private Double rating;

    public Book() {
    }

    public Book(Long book_id, String title, Set<Genre> genres, Set<Author> authors, Integer publishing_year, Integer pages, Double rating) {
        this.book_id = book_id;
        this.title = title;
        this.genres = genres;
        this.authors = authors;
        this.publishing_year = publishing_year;
        this.pages = pages;
        this.rating = rating;
    }

    public Book(String title, Set<Genre> genres, Set<Author> authors, Integer publishing_year, Integer pages, Double rating) {
        this.title = title;
        this.genres = genres;
        this.authors = authors;
        this.publishing_year = publishing_year;
        this.pages = pages;
        this.rating = rating;
    }
}
