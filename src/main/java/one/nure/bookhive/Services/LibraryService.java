package one.nure.bookhive.Services;

import one.nure.bookhive.Models.Book;
import one.nure.bookhive.Models.DataTransferObjects.BookDTO;
import one.nure.bookhive.Repositories.BookRepository;
import one.nure.bookhive.Models.Author;
import one.nure.bookhive.Models.Genre;
import one.nure.bookhive.Models.User;
import one.nure.bookhive.Repositories.UserRepository;
import one.nure.bookhive.Models.CompositeKeys.LibraryId;
import one.nure.bookhive.Models.Library;
import one.nure.bookhive.Models.DataTransferObjects.LibraryDTO;
import one.nure.bookhive.Repositories.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public LibraryDTO addBookToTheLibrary(Long bookId, UUID userId, String status) {
        Library library = new Library();
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found with id: " + userId));
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new IllegalArgumentException("Book not found with id " + bookId));

        LibraryId id = new LibraryId();
        id.setUser_id(userId);
        id.setBook_id(bookId);

        library.setId(id);
        library.setUser(user);
        library.setBook(book);
        library.setStatus(status);

        libraryRepository.save(library);
        return convertToLibraryDTO(library);
    }

    public Library updateBookStatus(LibraryId libraryId, String status) {
        try {
            Library updatedBook = libraryRepository.findById(libraryId).orElseThrow(() ->
                    new IllegalArgumentException("Book not found"));

            updatedBook.setStatus(status);
            return libraryRepository.save(updatedBook);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public LibraryDTO updatePagesRead(UUID userId, Long bookId, Integer pagesRead) {
        try {
            LibraryId libraryId = new LibraryId(userId, bookId);
            Library updatedBook = libraryRepository.findById(libraryId).orElseThrow(() ->
                    new IllegalArgumentException("Book not found"));

            updatedBook.setPages_read(pagesRead);
            return convertToLibraryDTO(libraryRepository.save(updatedBook));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public LibraryDTO updateBooksRating(UUID userId, Long bookId, Integer rating) {
        try {
            LibraryId libraryId = new LibraryId(userId, bookId);
            Library updatedBook = libraryRepository.findById(libraryId).orElseThrow(() ->
                    new IllegalArgumentException("Book not found"));

            updatedBook.setBook_rating(rating);
            return convertToLibraryDTO(libraryRepository.save(updatedBook));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookDTO> bookRecommendation(UUID userId) {
        try {
            List<Library> list = libraryRepository.findByUser_UserIdAndStatus(userId, "Читаю");
            Map<Genre, Integer> countGenres = new HashMap<>();
            for (Library bookInList : list) {
                Book book = bookInList.getBook();
                for (Genre genre : book.getGenres()) {
                    countGenres.put(genre, countGenres.getOrDefault(genre, 0) + 1);
                }
            }

            List<Genre> top3Genres = countGenres.entrySet().stream()
                    .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                    .map(Map.Entry::getKey)
                    .limit(3)
                    .toList();

            List<BookDTO> recommendedBooks = new ArrayList<>();

            for (Genre genre : top3Genres) {
                Set<Genre> singleGenreSet = new HashSet<>();
                singleGenreSet.add(genre);

                List<Book> books = bookRepository.findBookWithExactGenres(singleGenreSet, 1);
                for (Book book : books) {
                    if (!libraryRepository.existsByUser_UserIdAndBook_BookId(userId, book.getBookId())) {
                        recommendedBooks.add(convertToBookDTO(book));
                    }
                }
            }

            for (int i = 0; i < top3Genres.size(); i++) {
                for (int j = i + 1; j < top3Genres.size(); j++) {
                    Set<Genre> twoGenreSet = new HashSet<>();
                    twoGenreSet.add(top3Genres.get(i));
                    twoGenreSet.add(top3Genres.get(j));

                    List<Book> books = bookRepository.findBookWithExactGenres(twoGenreSet, 2);
                    for (Book book : books) {
                        if (!libraryRepository.existsByUser_UserIdAndBook_BookId(userId, book.getBookId())) {
                            recommendedBooks.add(convertToBookDTO(book));
                        }
                    }
                }
            }

            if (top3Genres.size() == 3) {
                Set<Genre> threeGenreSet = new HashSet<>(top3Genres);

                List<Book> books = bookRepository.findBookWithExactGenres(threeGenreSet, 3);
                for (Book book : books) {
                    if (!libraryRepository.existsByUser_UserIdAndBook_BookId(userId, book.getBookId())) {
                        recommendedBooks.add(convertToBookDTO(book));
                    }
                }
            }

            // По топ жанрам получить книги

            // Можно получать книги за теми жанрами, которых больше всего (по одному жанру, по несколько жанрам)
            // но книги должны быть топ по рейтингу, и их не должно быть в списках пользователя

            // В будущем лучше будет рекомендовать книги пользователей, которые прочитали пользователи
            // и имеют одинаковые книги в списках.
            // Типо взять у пользователя книги, пройтись по спискам других пользователей которые имеют эти книги
            // и от туда выбрать другие книги, которые они читали

            return recommendedBooks.stream()
                    .distinct()
                    .sorted(Comparator.comparing(BookDTO::getRating).reversed())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<LibraryDTO> getPlannedBooks(UUID userId) {
        List<Library> books = libraryRepository.findByUser_UserIdAndStatus(userId, "Заплановані");
        List<LibraryDTO> booksDTOs = new ArrayList<>();
        for (Library book : books) {
            booksDTOs.add(convertToLibraryDTO(book));
        }
        return booksDTOs;
    }

    public List<LibraryDTO> getReadingBooks(UUID userId) {
        List<Library> books = libraryRepository.findByUser_UserIdAndStatus(userId, "Читаю");
        List<LibraryDTO> booksDTOs = new ArrayList<>();
        for (Library book : books) {
            booksDTOs.add(convertToLibraryDTO(book));
        }
        return booksDTOs;
    }

    public List<LibraryDTO> getAbandonedBooks(UUID userId) {
        List<Library> books = libraryRepository.findByUser_UserIdAndStatus(userId, "Покинуті");
        List<LibraryDTO> booksDTOs = new ArrayList<>();
        for (Library book : books) {
            booksDTOs.add(convertToLibraryDTO(book));
        }
        return booksDTOs;
    }

    public static BookDTO convertToBookDTO(Book book) {
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

    //TODO: Need to be reviewed (why "Library book"?)
    public static LibraryDTO convertToLibraryDTO(Library book) {
        LibraryDTO bookDTO = new LibraryDTO();
        bookDTO.setBook(convertToBookDTO(book.getBook()));
        bookDTO.setPages_read(book.getPages_read());
        bookDTO.setBook_rating(book.getBook_rating());
        bookDTO.setStatus(book.getStatus());
        return bookDTO;
    }

    public void deleteBookFromLibrary(LibraryId libraryId) {
        try {
            libraryRepository.deleteById(libraryId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Book not found in list");
        }
    }
}