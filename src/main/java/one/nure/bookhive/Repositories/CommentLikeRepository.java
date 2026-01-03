package one.nure.bookhive.Repositories;

import one.nure.bookhive.Models.CommentLike;
import one.nure.bookhive.Models.CompositeKeys.LikeID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
// TODO: test all created queries
public interface CommentLikeRepository extends JpaRepository<CommentLike, LikeID> {
    @Query ("SELECT COALESCE(SUM(cl.value), 0) FROM CommentLike cl WHERE cl.id.comment_id = :commentID")
    Integer getSumOfLikesOfComment(Long commentID);
    byte getLikeValueOfUser(UUID userID);
}
