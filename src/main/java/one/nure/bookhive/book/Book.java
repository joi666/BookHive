package one.nure.bookhive.book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id;
    private String title;
    private String genre;
    private String author_fullname;
    private Integer publishing_year;
    private Integer pages;
    private Double rating;

    public Book() {
    }

    public Book(Long book_id, String title, String genre, String author_fullname, Integer publishing_year, Integer pages, Double rating) {
        this.book_id = book_id;
        this.title = title;
        this.genre = genre;
        this.author_fullname = author_fullname;
        this.publishing_year = publishing_year;
        this.pages = pages;
        this.rating = rating;
    }

    public Book(String title, String genre, String author_fullname, Integer publishing_year, Integer pages, Double rating) {
        this.title = title;
        this.genre = genre;
        this.author_fullname = author_fullname;
        this.publishing_year = publishing_year;
        this.pages = pages;
        this.rating = rating;
    }
}
