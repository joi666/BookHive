package one.nure.bookhive.Repositories;

import one.nure.bookhive.Models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
