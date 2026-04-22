package one.nure.bookhive.Models.CompositeKeys;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipId {
    private UUID userId1; // Sender
    private UUID userId2; // Recipient
}
