package one.nure.bookhive.comment;

import one.nure.bookhive.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByBook_BookId(Long bookId);
}
