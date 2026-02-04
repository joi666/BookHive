package one.nure.bookhive.Models.CompositeKeys;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikeID implements Serializable {

    private Long commentId;
    private UUID userId;

}
