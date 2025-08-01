package one.nure.bookhive.Repositories;

import one.nure.bookhive.Models.ListId;
import one.nure.bookhive.Models.ListOfBooks;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ListOfBooksRepository extends CrudRepository<ListOfBooks, ListId> {
    public List<ListOfBooks> findByUser_UserIdAndStatus(UUID userId, String status);
    public Boolean existsByUser_UserIdAndBook_BookId(UUID userId, Long bookId);
    public ListOfBooks findByUser_UserIdAndBook_BookId(UUID userId, Long bookId);
}
