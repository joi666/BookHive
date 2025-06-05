package one.nure.bookhive.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
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
