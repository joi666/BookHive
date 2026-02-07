package one.nure.bookhive.Models.DataTransferObjects;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthorShortDTO {

    private Long authorId;
    private String authorFullname;

}
