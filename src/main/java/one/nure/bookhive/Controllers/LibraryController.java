package one.nure.bookhive.Controllers;

import one.nure.bookhive.Services.LibraryService;
import one.nure.bookhive.Models.DataTransferObjects.BookDTO;
import one.nure.bookhive.Models.CompositeKeys.LibraryId;
import one.nure.bookhive.Models.Library;
import one.nure.bookhive.Models.DataTransferObjects.LibraryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v01/library")
// TODO: Test everything before deleting commented lines of code
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping(path = "/addBook/{bookId}")
    public LibraryDTO addBookToTheList(@PathVariable Long bookId, @RequestParam UUID userId, @RequestParam String status) {
        return libraryService.addBookToTheLibrary(bookId, userId, status);
    }

    @PutMapping(path = "/updateBookStatus/{libraryId}")
    public Library updateBookStatus(@PathVariable LibraryId libraryId, @RequestParam String status) {
        return libraryService.updateBookStatus(libraryId, status);
    }

//    @PutMapping(path = "/updatePagesRead/{listId}")
//    public ListOfBooks updatePagesRead(@PathVariable ListId listId, @RequestParam Integer pagesRead) {
//        return listOfBooksService.updatePagesRead(listId, pagesRead);
//    }
//
//    @PutMapping(path = "/updateRating/{listId}")
//    public ListOfBooks updateBooksRating(@PathVariable ListId listId, @RequestParam Integer rating) {
//        return listOfBooksService.updateBooksRating(listId, rating);
//    }

    @PutMapping(path = "/updatePagesRead")
    public LibraryDTO updatePagesRead(@RequestParam UUID userId, @RequestParam Long bookId, @RequestParam Integer pagesRead) {
        return libraryService.updatePagesRead(userId, bookId, pagesRead);
    }

    @PutMapping(path = "/updateRating")
    public LibraryDTO updateBooksRating(@RequestParam UUID userId, @RequestParam Long bookId, @RequestParam Integer rating) {
        return libraryService.updateBooksRating(userId, bookId, rating);
    }

    @GetMapping(path = "/recommendation/{userId}")
    public List<BookDTO> recommendation(@PathVariable UUID userId) {
        return libraryService.bookRecommendation(userId);
    }

    @GetMapping(path = "/getBooks/planned/{userId}")
    public List<LibraryDTO> getPlannedBooks(@PathVariable UUID userId) {
        return libraryService.getPlannedBooks(userId);
    }

    @GetMapping(path = "/getBooks/reading/{userId}")
    public List<LibraryDTO> getReadingBooks(@PathVariable UUID userId) {
        return libraryService.getReadingBooks(userId);
    }

    @GetMapping(path = "/getBooks/abandoned/{userId}")
    public List<LibraryDTO> getAbandonedBooks(@PathVariable UUID userId) {
        return libraryService.getAbandonedBooks(userId);
    }

    @DeleteMapping(path = "/delete/{libraryId}")
    public void deleteBookFromList(@PathVariable LibraryId libraryId) {
        libraryService.deleteBookFromLibrary(libraryId);
    }
}
