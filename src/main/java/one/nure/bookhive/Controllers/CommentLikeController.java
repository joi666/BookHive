package one.nure.bookhive.Controllers;

import one.nure.bookhive.Models.CommentLike;
import one.nure.bookhive.Models.CompositeKeys.LikeID;
import one.nure.bookhive.Services.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v01/likes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @Autowired
    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @GetMapping(path = "/getCommentLikes/{commentID}")
    public Integer getCommentLikes(@PathVariable Long commentID) {
        return commentLikeService.getCommentLikes(commentID);
    }

    @GetMapping(path = "/getUserLike/{userID}")
    public byte getUserLikes(@PathVariable UUID userID) {
        return commentLikeService.getUserLikes(userID);
    }

    @PostMapping
    public CommentLike addCommentLike(@RequestParam Long commentID, @RequestParam UUID userID, @RequestParam byte value) {
        return commentLikeService.addCommentLike(commentID, userID, value);
    }

    @PutMapping(path = "/updateLike/{commentID}")
    public CommentLike updateCommentLike(@PathVariable LikeID likeID, @RequestParam byte value) {
        return commentLikeService.updateCommentLike(likeID, value);
    }

    @DeleteMapping(path = "/deleteLike/{likeID}")
    public void deleteCommentLike(@PathVariable LikeID likeID) {
        commentLikeService.deleteCommentLike(likeID);
    }
}
