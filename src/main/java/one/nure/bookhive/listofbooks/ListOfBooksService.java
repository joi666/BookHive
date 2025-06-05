package one.nure.bookhive.listofbooks;

import one.nure.bookhive.book.Book;
import one.nure.bookhive.book.BookRepository;
import one.nure.bookhive.book.genre.Genre;
import one.nure.bookhive.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ListOfBooksService {

    private final ListOfBooksRepository listOfBooksRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ListOfBooksService(ListOfBooksRepository listOfBooksRepository, BookRepository bookRepository) {
        this.listOfBooksRepository = listOfBooksRepository;
        this.bookRepository = bookRepository;
    }

    public ListOfBooks addBookToTheList(User user, Book book, String status) {
        ListOfBooks listOfBooks = new ListOfBooks();

        listOfBooks.setUser(user);
        listOfBooks.setBook(book);
        listOfBooks.setStatus("status");

        return listOfBooksRepository.save(listOfBooks);
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

    public ListOfBooks updatePagesRead(ListId listId, Integer pagesRead) {
        try {
            ListOfBooks updatedBook = listOfBooksRepository.findById(listId).orElseThrow(() ->
                    new IllegalArgumentException("Book not found"));

            updatedBook.setPages_read(pagesRead);
            return listOfBooksRepository.save(updatedBook);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public ListOfBooks updateBooksRating(ListId listId, Integer rating) {
        try {
            ListOfBooks updatedBook = listOfBooksRepository.findById(listId).orElseThrow(() ->
                    new IllegalArgumentException("Book not found"));

            updatedBook.setBook_rating(rating);
            return listOfBooksRepository.save(updatedBook);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> bookRecommendation(UUID userId) {
        try {
            List<ListOfBooks> list = listOfBooksRepository.findByUser_UserIdAndStatus(userId, "");
            Map<Genre, Integer> countGenres = new HashMap<>();
            for (ListOfBooks bookInList : list) {
                Book book = bookInList.getBook();
                for (Genre genre : book.getGenres()) {
                    countGenres.put(genre, countGenres.getOrDefault(genre, 0) + 1);
                }
            }

            // Sort genres by their frequency
            List<Genre> sortedGenres = countGenres.entrySet().stream()
                    .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                    .map(Map.Entry::getKey)
                    .toList();

            List<Book> recommendedBooks = new ArrayList<>();

            // Create combines of genres
            for (int i = 0; i < Math.min(5, sortedGenres.size()); i++) {
                Genre g1 = sortedGenres.get(i);
                for (int j = i; j < Math.min(5, sortedGenres.size()); j++) {
                    Genre g2 = sortedGenres.get(j);
                    Set<Genre> genreSet = new HashSet<>();
                    genreSet.add(g1);
                    if (!g1.equals(g2)) genreSet.add(g2);

                    List<Book> books = bookRepository.findBookByGenres(genreSet);
                    for (Book book : books) {
                        if (!listOfBooksRepository.existsByUser_UserIdAndBook_BookId(userId, book.getBookId())) {
                            recommendedBooks.add(book);
                        }
                    }
                }
            }

            return recommendedBooks.stream()
                    .sorted(Comparator.comparing(Book::getRating).reversed())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ListOfBooks> getPlannedBooks(UUID userId) {
        return listOfBooksRepository.findByUser_UserIdAndStatus(userId, "Заплановані");
    }

    public List<ListOfBooks> getReadingBooks(UUID userId) {
        return listOfBooksRepository.findByUser_UserIdAndStatus(userId, "Читаю");
    }

    public List<ListOfBooks> getAbandonedBooks(UUID userId) {
        return listOfBooksRepository.findByUser_UserIdAndStatus(userId, "Покинуті");
    }

    public void deleteBookFromList(ListId listId) {
        try {
            listOfBooksRepository.deleteById(listId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Book not found in list");
        }
    }
}