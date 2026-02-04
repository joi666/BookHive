package one.nure.bookhive.Search;

import one.nure.bookhive.Models.Author;
import one.nure.bookhive.Models.Book;
import one.nure.bookhive.Models.DataTransferObjects.BookDTO;
import one.nure.bookhive.Models.User;
import one.nure.bookhive.Repositories.AuthorRepository;
import one.nure.bookhive.Repositories.BookRepository;
import one.nure.bookhive.Repositories.UserRepository;
import one.nure.bookhive.Services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public SearchService(AuthorRepository authorRepository, BookRepository bookRepository, UserRepository userRepository) {

        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;

    }

    public Search search(String input) {
        Search search = new Search();

        if (!input.isBlank()) {

            search.setAuthors(searchAuthors(input));
            search.setBooks(searchBooks(input));
            search.setUsers(searchUsers(input));

        }

        return search;
    }

    public List<Author> searchAuthors(String input) {
        return authorRepository.findAuthorsByAuthorFullnameContaining(input);
    }

    public List<BookDTO> searchBooks(String input) {

        List<BookDTO> bookResult = new ArrayList<>();

        List<Book> books = bookRepository.findBooksByTitleContains(input);

        for (Book book : books) {
            bookResult.add(LibraryService.convertToBookDTO(book));
        }
        return bookResult;
    }

    public List<User> searchUsers(String input) {
        return userRepository.findUsersByUserNameOrUserLastnameContains(input, input);
    }
}
