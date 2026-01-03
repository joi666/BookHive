package one.nure.bookhive.Repositories;

import one.nure.bookhive.Models.Book;
import one.nure.bookhive.Models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findBookByGenres(Set<Genre> genres);
    @Query("SELECT DISTINCT b FROM Book b WHERE " +
            "SIZE(b.genres) = :genreCount AND " +
            "(SELECT COUNT(g) FROM b.genres g WHERE g IN :genres) = :genreCount")
    List<Book> findBookWithExactGenres(@Param("genres") Set<Genre> genres,
                                       @Param("genreCount") long genreCount);
}
