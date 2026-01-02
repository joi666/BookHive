package one.nure.bookhive.Repositories;

import one.nure.bookhive.Models.LibraryId;
import one.nure.bookhive.Models.Library;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface LibraryRepository extends CrudRepository<Library, LibraryId> {
    public List<Library> findByUser_UserIdAndStatus(UUID userId, String status);
    public Boolean existsByUser_UserIdAndBook_BookId(UUID userId, Long bookId);
    public Library findByUser_UserIdAndBook_BookId(UUID userId, Long bookId);
}
