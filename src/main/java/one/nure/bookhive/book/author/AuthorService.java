package one.nure.bookhive.book.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long authorId, Author author) {
        Author existingAuthor = authorRepository.findById(authorId).orElseThrow(() ->
                new IllegalArgumentException("Author not found with id: " + author.getAuthor_id()));

        existingAuthor.setAuthor_fullname(author.getAuthor_fullname());
        existingAuthor.setBooks(author.getBooks());

        return authorRepository.save(existingAuthor);
    }
}
