package one.nure.bookhive.Models.DataTransferObjects;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipDTO {

    private UUID userId1; // Sender
    private UUID userId2; // Recipient
    private LocalDate creation_date;
    private String status;
}
