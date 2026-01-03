package one.nure.bookhive.Repositories;

import one.nure.bookhive.Models.CompositeKeys.LibraryId;
import one.nure.bookhive.Models.Library;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LibraryRepository extends CrudRepository<Library, LibraryId> {
    public List<Library> findByUser_UserIdAndStatus(UUID userId, String status);
    public Boolean existsByUser_UserIdAndBook_BookId(UUID userId, Long bookId);
    public Library findByUser_UserIdAndBook_BookId(UUID userId, Long bookId);
}
