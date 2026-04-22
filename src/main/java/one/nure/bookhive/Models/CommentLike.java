package one.nure.bookhive.Models;

import jakarta.persistence.*;
import lombok.*;
import one.nure.bookhive.Models.CompositeKeys.LikeID;

import java.util.UUID;

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

    @OneToOne
    @MapsId("commentId")
    @JoinColumn(name = "comment_id")
    private Long commentId;

    @OneToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UUID userId;

    private byte value;

}
