package one.nure.bookhive.Search;

import lombok.*;
import one.nure.bookhive.Models.Author;
import one.nure.bookhive.Models.Book;
import one.nure.bookhive.Models.DataTransferObjects.BookDTO;
import one.nure.bookhive.Models.User;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Search {

    private List<Author> authors;
    private List<BookDTO> books;
    private List<User> users;

}
