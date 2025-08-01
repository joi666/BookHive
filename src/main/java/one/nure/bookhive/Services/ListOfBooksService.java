package one.nure.bookhive.Services;

import one.nure.bookhive.Models.Book;
import one.nure.bookhive.Models.BookDTO;
import one.nure.bookhive.Repositories.BookRepository;
import one.nure.bookhive.Models.Author;
import one.nure.bookhive.Models.Genre;
import one.nure.bookhive.Models.User;
import one.nure.bookhive.Repositories.UserRepository;
import one.nure.bookhive.Models.ListId;
import one.nure.bookhive.Models.ListOfBooks;
import one.nure.bookhive.Models.ListOfBooksDTO;
import one.nure.bookhive.Repositories.ListOfBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ListOfBooksService {

    private final ListOfBooksRepository listOfBooksRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public ListOfBooksService(ListOfBooksRepository listOfBooksRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.listOfBooksRepository = listOfBooksRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public ListOfBooksDTO addBookToTheList(Long bookId, UUID userId, String status) {
        ListOfBooks listOfBooks = new ListOfBooks();
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found with id: " + userId));
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new IllegalArgumentException("Book not found with id " + bookId));

        ListId id = new ListId();
        id.setUser_id(userId);
        id.setBook_id(bookId);

        listOfBooks.setId(id);
        listOfBooks.setUser(user);
        listOfBooks.setBook(book);
        listOfBooks.setStatus(status);

        listOfBooksRepository.save(listOfBooks);
        return convertToListOfBooksDTO(listOfBooks);
    }

    public ListOfBooks updateBookStatus(ListId listId, String status) {
        try {
            ListOfBooks updatedBook = listOfBooksRepository.findById(listId).orElseThrow(() ->
                    new IllegalArgumentException("Book not found"));

            updatedBook.setStatus(status);
            return listOfBooksRepository.save(updatedBook);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public ListOfBooksDTO updatePagesRead(UUID userId, Long bookId, Integer pagesRead) {
        try {
            ListId listId = new ListId(userId, bookId);
            ListOfBooks updatedBook = listOfBooksRepository.findById(listId).orElseThrow(() ->
                    new IllegalArgumentException("Book not found"));

            updatedBook.setPages_read(pagesRead);
            return convertToListOfBooksDTO(listOfBooksRepository.save(updatedBook));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public ListOfBooksDTO updateBooksRating(UUID userId, Long bookId, Integer rating) {
        try {
            ListId listId = new ListId(userId, bookId);
            ListOfBooks updatedBook = listOfBooksRepository.findById(listId).orElseThrow(() ->
                    new IllegalArgumentException("Book not found"));

            updatedBook.setBook_rating(rating);
            return convertToListOfBooksDTO(listOfBooksRepository.save(updatedBook));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookDTO> bookRecommendation(UUID userId) {
        try {
            List<ListOfBooks> list = listOfBooksRepository.findByUser_UserIdAndStatus(userId, "Читаю");
            Map<Genre, Integer> countGenres = new HashMap<>();
            for (ListOfBooks bookInList : list) {
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
                    if (!listOfBooksRepository.existsByUser_UserIdAndBook_BookId(userId, book.getBookId())) {
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
                        if (!listOfBooksRepository.existsByUser_UserIdAndBook_BookId(userId, book.getBookId())) {
                            recommendedBooks.add(convertToBookDTO(book));
                        }
                    }
                }
            }

            if (top3Genres.size() == 3) {
                Set<Genre> threeGenreSet = new HashSet<>(top3Genres);

                List<Book> books = bookRepository.findBookWithExactGenres(threeGenreSet, 3);
                for (Book book : books) {
                    if (!listOfBooksRepository.existsByUser_UserIdAndBook_BookId(userId, book.getBookId())) {
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

    public List<ListOfBooksDTO> getPlannedBooks(UUID userId) {
        List<ListOfBooks> books = listOfBooksRepository.findByUser_UserIdAndStatus(userId, "Заплановані");
        List<ListOfBooksDTO> booksDTOs = new ArrayList<>();
        for (ListOfBooks book : books) {
            booksDTOs.add(convertToListOfBooksDTO(book));
        }
        return booksDTOs;
    }

    public List<ListOfBooksDTO> getReadingBooks(UUID userId) {
        List<ListOfBooks> books = listOfBooksRepository.findByUser_UserIdAndStatus(userId, "Читаю");
        List<ListOfBooksDTO> booksDTOs = new ArrayList<>();
        for (ListOfBooks book : books) {
            booksDTOs.add(convertToListOfBooksDTO(book));
        }
        return booksDTOs;
    }

    public List<ListOfBooksDTO> getAbandonedBooks(UUID userId) {
        List<ListOfBooks> books = listOfBooksRepository.findByUser_UserIdAndStatus(userId, "Покинуті");
        List<ListOfBooksDTO> booksDTOs = new ArrayList<>();
        for (ListOfBooks book : books) {
            booksDTOs.add(convertToListOfBooksDTO(book));
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

    public static ListOfBooksDTO convertToListOfBooksDTO(ListOfBooks book) {
        ListOfBooksDTO bookDTO = new ListOfBooksDTO();
        bookDTO.setBook(convertToBookDTO(book.getBook()));
        bookDTO.setPages_read(book.getPages_read());
        bookDTO.setBook_rating(book.getBook_rating());
        bookDTO.setStatus(book.getStatus());
        return bookDTO;
    }

    public void deleteBookFromList(ListId listId) {
        try {
            listOfBooksRepository.deleteById(listId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Book not found in list");
        }
    }
}