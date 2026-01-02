package one.nure.bookhive.Controllers;

import one.nure.bookhive.Models.Comment;
import one.nure.bookhive.Models.CommentDTO;
import one.nure.bookhive.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v01/comments")
/* TODO: Test everything before deleting commented lines of code, add a new logic for likes */
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(path = "/{bookId}")
    public List<CommentDTO> getBookComments(@PathVariable Long bookId) {
        return commentService.findByBook(bookId);
    }

    @PostMapping(path = "/create/{bookId}")
    public CommentDTO createComment(@PathVariable Long bookId, @RequestParam UUID userId, @RequestParam String commentBody) {
        return commentService.createComment(bookId, userId, commentBody);
    }

//    @PutMapping(path = "/updateComment/{commentId}")
//    public Comment updateCommentLikes(@PathVariable Long commentId, @RequestParam Integer likeValue) {
//        return commentService.updateCommentLikes(commentId, likeValue);
//    }

}
