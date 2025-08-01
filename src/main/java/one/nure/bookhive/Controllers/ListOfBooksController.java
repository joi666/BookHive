package one.nure.bookhive.Controllers;

import one.nure.bookhive.Services.ListOfBooksService;
import one.nure.bookhive.Models.BookDTO;
import one.nure.bookhive.Models.ListId;
import one.nure.bookhive.Models.ListOfBooks;
import one.nure.bookhive.Models.ListOfBooksDTO;
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

    @PostMapping(path = "/addBook/{bookId}")
    public ListOfBooksDTO addBookToTheList(@PathVariable Long bookId, @RequestParam UUID userId, @RequestParam String status) {
        return listOfBooksService.addBookToTheList(bookId, userId, status);
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
    public List<BookDTO> recommendation(@PathVariable UUID userId) {
        return listOfBooksService.bookRecommendation(userId);
    }

    @GetMapping(path = "/getBooks/planned/{userId}")
    public List<ListOfBooksDTO> getPlannedBooks(@PathVariable UUID userId) {
        return listOfBooksService.getPlannedBooks(userId);
    }

    @GetMapping(path = "/getBooks/reading/{userId}")
    public List<ListOfBooksDTO> getReadingBooks(@PathVariable UUID userId) {
        return listOfBooksService.getReadingBooks(userId);
    }

    @GetMapping(path = "/getBooks/abandoned/{userId}")
    public List<ListOfBooksDTO> getAbandonedBooks(@PathVariable UUID userId) {
        return listOfBooksService.getAbandonedBooks(userId);
    }

    @DeleteMapping(path = "/delete/{listId}")
    public void deleteBookFromList(@PathVariable ListId listId) {
        listOfBooksService.deleteBookFromList(listId);
    }
}
