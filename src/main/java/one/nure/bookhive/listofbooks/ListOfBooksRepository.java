package one.nure.bookhive.listofbooks;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ListOfBooksRepository extends CrudRepository<ListOfBooks, ListId> {
    public List<ListOfBooks> findByUser_UserIdAndStatus(UUID userId, String status);
    public Boolean existsByUser_UserIdAndBook_BookId(UUID userId, Long bookId);
    public List<ListOfBooks> findByStatusAndUser_UserId(String status, UUID userId);
}
