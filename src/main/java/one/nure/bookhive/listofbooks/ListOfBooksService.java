package one.nure.bookhive.listofbooks;

import one.nure.bookhive.book.Book;
import one.nure.bookhive.book.BookRepository;
import one.nure.bookhive.book.genre.Genre;
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

    public ListOfBooks updateList(ListOfBooks listOfBooks) {
        ListOfBooks existingList = listOfBooksRepository.findById(listOfBooks.getId()).orElseThrow(() ->
                new IllegalArgumentException("List not found with id: " + listOfBooks.getId()));
        //insert new data to existing list
        return listOfBooksRepository.save(existingList);
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

//    public Iterable<ListOfBooks> getLists() {
//        return listOfBooksRepository.findAll();
//    }

//    public ListOfBooks addBookToList(ListOfBooks listOfBooks) {
//        return listOfBooksRepository.save(listOfBooks);
//    }

//    public void deleteBookFromList(ListId listId) {
//        if (listOfBooksRepository.existsById(listId)) {
//            throw new IllegalArgumentException("List not found with id: " + listId);
//        }
//        listOfBooksRepository.deleteById(listId);
//    }

}
