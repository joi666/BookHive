package one.nure.bookhive.Models.DataTransferObjects;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LibraryDTO {
    private BookDTO book;
    private String status;
    private Integer pages_read;
    private Integer book_rating;
}
