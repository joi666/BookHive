package one.nure.bookhive.listofbooks;

import lombok.*;
import one.nure.bookhive.book.BookDTO;

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
