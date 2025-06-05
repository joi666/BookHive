package one.nure.bookhive.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v01/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(path = "/{bookId}")
    public List<Comment> getBookComments(@PathVariable Long bookId) {
        return commentService.findByBook(bookId);
    }

    @PostMapping(path = "/create")
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @PutMapping(path = "/updateComment/{commentId}")
    public Comment updateCommentLikes(@PathVariable Long commentId, @RequestParam Integer likeValue) {
        return commentService.updateCommentLikes(commentId, likeValue);
    }

//    @GetMapping
//    public List<Comment> getComments() {
//        return commentService.getComments();
//    }

//    @PutMapping
//    public Comment updateComment(@RequestBody Comment comment) {
//        return commentService.updateComment(comment);
//    }

//    @DeleteMapping
//    public void deleteComment(@RequestParam Long comment_id) {
//        commentService.deleteComment(comment_id);
//    }
}
