package one.nure.bookhive.comment;

import one.nure.bookhive.book.Book;
import one.nure.bookhive.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    public List<Comment> findByBook(Long bookId) {
        try {
            Book book = bookRepository.findById(bookId).orElseThrow(() ->
                    new IllegalArgumentException("Book not found"));
            return commentRepository.findByBook(book);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateCommentLikes(Long commentId, Integer likeValue) {
        try {
            Comment existingComment = commentRepository.findById(commentId).orElseThrow(() ->
                    new IllegalArgumentException("Comment not found"));
            existingComment.setComment_likes(existingComment.getComment_likes() + likeValue);
            return commentRepository.save(existingComment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public List<Comment> getComments() {
//        return commentRepository.findAll();
//    }

//    public Comment updateComment(Comment comment) {
//        Comment existingComment = commentRepository.findById(comment.getComment_id()).orElseThrow(() ->
//                new IllegalArgumentException("Comment not found with id: " + comment.getComment_id()));
//        //insert new data to existing comment
//        return commentRepository.save(existingComment);
//    }

//    public void deleteComment(Long comment_id) {
//        if (commentRepository.existsById(comment_id)) {
//            throw new IllegalArgumentException("Comment not found with id: " + comment_id);
//        }
//        commentRepository.deleteById(comment_id);
//    }
}
