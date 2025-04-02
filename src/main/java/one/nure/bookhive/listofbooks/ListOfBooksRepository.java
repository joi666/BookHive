package one.nure.bookhive.listofbooks;

import org.springframework.data.repository.CrudRepository;

public interface ListOfBooksRepository extends CrudRepository<ListOfBooks, ListId> {
}
