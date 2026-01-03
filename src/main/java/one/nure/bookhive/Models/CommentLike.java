package one.nure.bookhive.Models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import one.nure.bookhive.Models.CompositeKeys.LikeID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment_likes")
// TODO: create a table "comment_likes" and delete "comment_likes" column from comments
public class CommentLike {

    @EmbeddedId
    private LikeID id;

    private byte value;

}
