package one.nure.bookhive.listofbooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v01/listsofbooks")
public class ListOfBooksController {

    private final ListOfBooksService listOfBooksService;

    @Autowired
    public ListOfBooksController(ListOfBooksService listOfBooksService) {
        this.listOfBooksService = listOfBooksService;
    }

    @GetMapping
    public Iterable<ListOfBooks> getListOfBooks() {
        return listOfBooksService.getLists();
    }

    @PostMapping
    public ListOfBooks createListOfBooks(@RequestBody ListOfBooks listOfBooks) {
        return listOfBooksService.addBookToList(listOfBooks);
    }

    @PutMapping
    public ListOfBooks updateListOfBooks(@RequestBody ListOfBooks listOfBooks) {
        return listOfBooksService.updateList(listOfBooks);
    }

    @DeleteMapping
    public void deleteBookFromList(@RequestParam ListId listId) {
        listOfBooksService.deleteBookFromList(listId);
    }
}
