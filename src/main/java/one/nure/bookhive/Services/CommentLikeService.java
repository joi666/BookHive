package one.nure.bookhive.Services;

import one.nure.bookhive.Models.CommentLike;
import one.nure.bookhive.Models.CompositeKeys.LikeID;
import one.nure.bookhive.Repositories.CommentLikeRepository;
import one.nure.bookhive.Repositories.CommentRepository;
import one.nure.bookhive.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Service
// TODO: test functionality of business logic
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentLikeService(CommentLikeRepository commentLikeRepository,
                              UserRepository userRepository,
                              CommentRepository commentRepository) {
        this.commentLikeRepository = commentLikeRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public Integer getCommentLikes(Long commentID) {
        if (!commentRepository.existsById(commentID)) {
            throw new IllegalArgumentException("Comment not found with id: " + commentID);
        }

        return commentLikeRepository.getSumOfLikesOfComment(commentID);
    }

    public byte getUserLikes(UUID userID) {
        if (!userRepository.existsById(userID)) {
            throw new IllegalArgumentException("User not found with id: " + userID);
        }
        return commentLikeRepository.getLikeValueOfUser(userID);
    }

    public CommentLike addCommentLike(Long commentID, UUID userID, byte value) {
        if (!commentRepository.existsById(commentID)) {
            throw new IllegalArgumentException("Comment not found with id: " + commentID);
        }
        if (!userRepository.existsById(userID)) {
            throw new IllegalArgumentException("User not found with id: " + userID);
        }

        LikeID likeID = new LikeID(commentID, userID);
        CommentLike commentLike = new CommentLike(likeID, value);

        return commentLikeRepository.save(commentLike);
    }

    public CommentLike updateCommentLike(LikeID likeID, byte value) {

        CommentLike existingCommentLike = commentLikeRepository.findById(likeID).orElseThrow(() ->
                new IllegalArgumentException("Like not found with id: " + likeID));

        existingCommentLike.setValue(value);

        return commentLikeRepository.save(existingCommentLike);
    }

    public void deleteCommentLike(LikeID likeID) {
        if (!commentLikeRepository.existsById(likeID)) {
            throw new IllegalArgumentException("Like not found with id: " + likeID);
        }
        commentLikeRepository.deleteById(likeID);
    }
}
