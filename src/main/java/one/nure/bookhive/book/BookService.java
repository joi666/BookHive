package one.nure.bookhive.book;

import one.nure.bookhive.book.author.Author;
import one.nure.bookhive.book.genre.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

//    public List<Book> getBooks() {
//        return bookRepository.findAll();
//    }

    public List<BookDTO> getBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToSummaryDTO)
                .collect(Collectors.toList());
    }

    private BookDTO convertToSummaryDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setBookId(book.getBookId());
        dto.setTitle(book.getTitle());
        dto.setPublishing_year(book.getPublishing_year());
        dto.setPages(book.getPages());
        dto.setRating(book.getRating());

        Set<String> genres = book.getGenres().stream()
                .map(Genre::getGenreName)
                .collect(Collectors.toSet());

        Set<String> authors = book.getAuthors().stream()
                .map(Author::getAuthor_fullname)
                .collect(Collectors.toSet());

        dto.setGenres(genres);
        dto.setAuthors(authors);

        return dto;
    }

    public BookDTO getBookById(Long bookId) {
        return convertToSummaryDTO(bookRepository.findById(bookId).orElseThrow(() ->
                new IllegalArgumentException("Book not found with id: " + bookId)));
    }

    public Book createBook (Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook (Long bookId, Book book) {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(() ->
                new IllegalArgumentException("Book not found with id: " + book.getBookId()));

        existingBook.setGenres(book.getGenres());
        existingBook.setAuthors(book.getAuthors());
        existingBook.setTitle(book.getTitle());
        existingBook.setPublishing_year(book.getPublishing_year());
        existingBook.setPages(book.getPages());
        existingBook.setRating(book.getRating());

        return bookRepository.save(existingBook);
    }

    public void deleteBook (Long book_id) {
        if (!bookRepository.existsById(book_id)) {
            throw new IllegalArgumentException("Book not found with id: " + book_id);
        }
        bookRepository.deleteById(book_id);
    }
}
