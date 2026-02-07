package one.nure.bookhive.Models.DataTransferObjects;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID userId;
    private String email;
    private LocalDate accountCreationDate;
    private String userName;
    private String userLastname;

}
