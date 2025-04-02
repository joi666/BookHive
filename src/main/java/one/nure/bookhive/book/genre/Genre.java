package one.nure.bookhive.book.genre;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import one.nure.bookhive.book.author.Author;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genre_id;

    private String genre_name;

    @ManyToMany(mappedBy = "genres")
    private Set<Author> authors;

    public Genre() {
    }

    public Genre(Long genre_id, String genre_name) {
        this.genre_id = genre_id;
        this.genre_name = genre_name;
    }

    public Genre(String genre_name) {
        this.genre_name = genre_name;
    }
}
