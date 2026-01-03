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
public class LibraryId implements Serializable {

    private UUID user_id;
    private Long book_id;

}
