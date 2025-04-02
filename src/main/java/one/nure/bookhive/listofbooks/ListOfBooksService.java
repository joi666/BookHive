package one.nure.bookhive.listofbooks;

import one.nure.bookhive.comment.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListOfBooksService {

    private final ListOfBooksRepository listOfBooksRepository;

    @Autowired
    public ListOfBooksService(ListOfBooksRepository listOfBooksRepository) {
        this.listOfBooksRepository = listOfBooksRepository;
    }

    public Iterable<ListOfBooks> getLists() {
        return listOfBooksRepository.findAll();
    }

    public ListOfBooks addBookToList(ListOfBooks listOfBooks) {
        return listOfBooksRepository.save(listOfBooks);
    }

    public ListOfBooks updateList(ListOfBooks listOfBooks) {
        ListOfBooks existingList = listOfBooksRepository.findById(listOfBooks.getId()).orElseThrow(() ->
                new IllegalArgumentException("List not found with id: " + listOfBooks.getId()));
        //insert new data to existing list
        return listOfBooksRepository.save(existingList);
    }

    public void deleteBookFromList(ListId listId) {
        if (listOfBooksRepository.existsById(listId)) {
            throw new IllegalArgumentException("List not found with id: " + listId);
        }
        listOfBooksRepository.deleteById(listId);
    }
}
