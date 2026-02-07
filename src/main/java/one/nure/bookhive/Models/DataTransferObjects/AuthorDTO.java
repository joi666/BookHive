package one.nure.bookhive.Models.DataTransferObjects;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    private Long authorId;
    private String authorFullname;
    private Set<BookShortDTO> books;

}
