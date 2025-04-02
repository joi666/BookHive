package one.nure.bookhive.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment) {
        Comment existingComment = commentRepository.findById(comment.getComment_id()).orElseThrow(() ->
                new IllegalArgumentException("Comment not found with id: " + comment.getComment_id()));
        //insert new data to existing comment
        return commentRepository.save(existingComment);
    }

    public void deleteComment(Long comment_id) {
        if (commentRepository.existsById(comment_id)) {
            throw new IllegalArgumentException("Comment not found with id: " + comment_id);
        }
        commentRepository.deleteById(comment_id);
    }
}
