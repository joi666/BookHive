package one.nure.bookhive.book.genre;

import jakarta.persistence.*;
import lombok.*;
import one.nure.bookhive.book.Book;
import one.nure.bookhive.book.author.Author;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genre_id;

    @Column(name = "genre_name")
    private String genreName;

    @ManyToMany(mappedBy = "genres")
    private Set<Book> books;
}
