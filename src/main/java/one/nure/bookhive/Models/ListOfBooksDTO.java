package one.nure.bookhive.Models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ListOfBooksDTO {
    private BookDTO book;
    private String status;
    private Integer pages_read;
    private Integer book_rating;
}
