package one.nure.bookhive.Services;

import one.nure.bookhive.Models.Book;
import one.nure.bookhive.Models.DataTransferObjects.AuthorShortDTO;
import one.nure.bookhive.Models.DataTransferObjects.BookDTO;
import one.nure.bookhive.Repositories.BookRepository;
import one.nure.bookhive.Models.Author;
import one.nure.bookhive.Models.Genre;
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

    public List<BookDTO> getBooks() {
        return bookRepository.findAll().stream()
                .map(BookService::convertToBookDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long bookId) {
        return convertToBookDTO(bookRepository.findById(bookId).orElseThrow(() ->
                new IllegalArgumentException("Book not found with id: " + bookId)));
    }

    public BookDTO createBook (Book book) {
        return convertToBookDTO(bookRepository.save(book));
    }

    public BookDTO updateBook (Long bookId, Book book) {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(() ->
                new IllegalArgumentException("Book not found with id: " + book.getBookId()));

        existingBook.setGenres(book.getGenres());
        existingBook.setAuthors(book.getAuthors());
        existingBook.setTitle(book.getTitle());
        existingBook.setPublishing_year(book.getPublishing_year());
        existingBook.setPages(book.getPages());
        existingBook.setRating(book.getRating());

        return convertToBookDTO(bookRepository.save(existingBook));
    }

    public void deleteBook (Long book_id) {
        if (!bookRepository.existsById(book_id)) {
            throw new IllegalArgumentException("Book not found with id: " + book_id);
        }
        bookRepository.deleteById(book_id);
    }

    public static BookDTO convertToBookDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setBookId(book.getBookId());
        dto.setTitle(book.getTitle());
        dto.setPublishingYear(book.getPublishing_year());
        dto.setPages(book.getPages());
        dto.setRating(book.getRating());

        Set<String> genres = book.getGenres().stream()
                .map(Genre::getGenreName)
                .collect(Collectors.toSet());

        Set<AuthorShortDTO> authors = book.getAuthors().stream()
                .map(author -> new AuthorShortDTO(author.getAuthor_id(), author.getAuthorFullname()))
                .collect(Collectors.toSet());

        dto.setGenres(genres);
        dto.setAuthors(authors);

        return dto;
    }
}
