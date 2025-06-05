package one.nure.bookhive.book;

import one.nure.bookhive.book.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findBookByGenres(Set<Genre> genres);
}
