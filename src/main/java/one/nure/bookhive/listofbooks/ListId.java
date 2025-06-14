package one.nure.bookhive.listofbooks;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@ToString
public class ListId implements Serializable {

    private UUID user_id;
    private Long book_id;

    public ListId() {
    }

    public ListId(UUID user_id, Long book_id) {
        this.user_id = user_id;
        this.book_id = book_id;
    }
}
