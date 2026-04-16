package one.nure.bookhive.Models.DataTransferObjects;

import jakarta.persistence.*;
import lombok.*;
import one.nure.bookhive.Models.Dialog;
import one.nure.bookhive.Models.User;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private Long id;
    private Long dialogId;
    private UUID userId;
    private String content;
    private Instant created_at;
    private Boolean isRead = false;

}
