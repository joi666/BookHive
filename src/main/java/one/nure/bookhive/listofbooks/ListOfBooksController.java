package one.nure.bookhive.listofbooks;

import one.nure.bookhive.book.Book;
import one.nure.bookhive.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v01/listsofbooks")
public class ListOfBooksController {

    private final ListOfBooksService listOfBooksService;

    @Autowired
    public ListOfBooksController(ListOfBooksService listOfBooksService) {
        this.listOfBooksService = listOfBooksService;
    }

    @PostMapping(path = "/addBook")
    public ListOfBooks addBookToTheList(@RequestParam User user, @RequestParam Book book, @RequestParam String status) {
        return listOfBooksService.addBookToTheList(user, book, status);
    }

    @PutMapping(path = "/updateBookStatus/{listId}")
    public ListOfBooks updateBookStatus(@PathVariable ListId listId, @RequestParam String status) {
        return listOfBooksService.updateBookStatus(listId, status);
    }

    @PutMapping(path = "/updatePagesRead/{listId}")
    public ListOfBooks updatePagesRead(@PathVariable ListId listId, @RequestParam Integer pagesRead) {
        return listOfBooksService.updatePagesRead(listId, pagesRead);
    }

    @PutMapping(path = "/updateRating/{listId}")
    public ListOfBooks updateBooksRating(@PathVariable ListId listId, @RequestParam Integer rating) {
        return listOfBooksService.updateBooksRating(listId, rating);
    }

    @GetMapping(path = "/recommendation/{userId}")
    public List<Book> recommendation(@PathVariable UUID userId) {
        return listOfBooksService.bookRecommendation(userId);
    }

    @GetMapping(path = "/getBooks/planned/{userId}")
    public List<ListOfBooks> getPlannedBooks(@PathVariable UUID userId) {
        return listOfBooksService.getPlannedBooks(userId);
    }

    @GetMapping(path = "/getBooks/reading/{userId}")
    public List<ListOfBooks> getReadingBooks(@PathVariable UUID userId) {
        return listOfBooksService.getReadingBooks(userId);
    }

    @GetMapping(path = "/getBooks/abandoned/{userId}")
    public List<ListOfBooks> getAbandonedBooks(@PathVariable UUID userId) {
        return listOfBooksService.getAbandonedBooks(userId);
    }

    @DeleteMapping(path = "/delete/{listId}")
    public void deleteBookFromList(@PathVariable ListId listId) {
        listOfBooksService.deleteBookFromList(listId);
    }
}
