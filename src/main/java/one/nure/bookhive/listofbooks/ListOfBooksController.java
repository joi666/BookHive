package one.nure.bookhive.listofbooks;

import one.nure.bookhive.book.Book;
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

    @PutMapping(path = "/update")
    public ListOfBooks updateListOfBooks(@RequestBody ListOfBooks listOfBooks) {
        return listOfBooksService.updateList(listOfBooks);
    }

    @GetMapping(path = "/recommendation/{userId}")
    public List<Book> recommendation(@PathVariable UUID userId) {
        return listOfBooksService.bookRecommendation(userId);
    }

//    @GetMapping
//    public Iterable<ListOfBooks> getListOfBooks() {
//        return listOfBooksService.getLists();
//    }

//    @PostMapping
//    public ListOfBooks createListOfBooks(@RequestBody ListOfBooks listOfBooks) {
//        return listOfBooksService.addBookToList(listOfBooks);
//    }

//    @DeleteMapping
//    public void deleteBookFromList(@RequestParam ListId listId) {
//        listOfBooksService.deleteBookFromList(listId);
//    }

}
