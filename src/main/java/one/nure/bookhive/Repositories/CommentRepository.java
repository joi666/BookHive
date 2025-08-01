package one.nure.bookhive.Repositories;

import one.nure.bookhive.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByBook_BookId(Long bookId);
}
