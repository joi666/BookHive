package one.nure.bookhive.book;

import one.nure.bookhive.book.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findBookByGenres(Set<Genre> genres);
    @Query("SELECT DISTINCT b FROM Book b WHERE " +
            "SIZE(b.genres) = :genreCount AND " +
            "(SELECT COUNT(g) FROM b.genres g WHERE g IN :genres) = :genreCount")
    List<Book> findBookWithExactGenres(@Param("genres") Set<Genre> genres,
                                       @Param("genreCount") long genreCount);
}
