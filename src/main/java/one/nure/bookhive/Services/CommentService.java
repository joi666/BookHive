package one.nure.bookhive.Services;

import one.nure.bookhive.Models.Comment;
import one.nure.bookhive.Models.CommentDTO;
import one.nure.bookhive.Repositories.CommentRepository;
import one.nure.bookhive.Models.Book;
import one.nure.bookhive.Repositories.BookRepository;
import one.nure.bookhive.Models.ListOfBooks;
import one.nure.bookhive.Repositories.ListOfBooksRepository;
import one.nure.bookhive.Models.User;
import one.nure.bookhive.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ListOfBooksRepository listOfBooksRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, BookRepository bookRepository, ListOfBooksRepository listOfBooksRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.listOfBooksRepository = listOfBooksRepository;
        this.userRepository = userRepository;
    }

    public List<CommentDTO> findByBook(Long bookId) {
        try {
            List<Comment> comment = commentRepository.findByBook_BookId(bookId);
            return convertListOfCommentsDTO(comment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not find comment");
        }
    }

    private List<CommentDTO> convertListOfCommentsDTO(List<Comment> comments) {
        List<CommentDTO> commentsDTO = new ArrayList<>();

        for (Comment comment : comments) {
            User user = comment.getUser();
            Book book = comment.getBook();
            ListOfBooks listOfBooks = listOfBooksRepository.findByUser_UserIdAndBook_BookId(user.getUserId(),
                    book.getBookId());
            CommentDTO commentDTO = getCommentDTO(comment, user, listOfBooks);

            commentsDTO.add(commentDTO);
        }
        return commentsDTO;
    }

    private CommentDTO convertToCommentDTO(Comment comment, User user) {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setComment_id(comment.getComment_id());
        commentDTO.setComment_date(comment.getComment_date());
        commentDTO.setComment_body(comment.getComment_body());
        commentDTO.setComment_likes(comment.getComment_likes());
        commentDTO.setUser_name(user.getUser_name());
        commentDTO.setUser_lastname(user.getUser_lastname());

        return commentDTO;
    }

    private static CommentDTO getCommentDTO(Comment comment, User user, ListOfBooks listOfBooks) {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setComment_id(comment.getComment_id());
        commentDTO.setComment_date(comment.getComment_date());
        commentDTO.setComment_body(comment.getComment_body());
        commentDTO.setComment_likes(comment.getComment_likes());
        commentDTO.setUser_name(user.getUser_name());
        commentDTO.setUser_lastname(user.getUser_lastname());
        if (listOfBooks.getBook_rating() != null) {
            commentDTO.setBook_rating(listOfBooks.getBook_rating());
        }
        return commentDTO;
    }

    public CommentDTO createComment(Long bookId, UUID userId, String commentBody) {
        try {
            Comment comment = new Comment();
            Book book = bookRepository.findById(bookId).orElseThrow(() ->
                    new IllegalArgumentException("Book not found"));
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new IllegalArgumentException("User not found"));

            comment.setBook(book);
            comment.setUser(user);
            comment.setComment_body(commentBody);
            comment.setComment_date(LocalDate.now());
            comment.setComment_likes(0);

            commentRepository.save(comment);
            return convertToCommentDTO(comment, user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
}
