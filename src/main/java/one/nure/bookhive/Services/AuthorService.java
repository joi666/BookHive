package one.nure.bookhive.Services;

import one.nure.bookhive.Models.Author;
import one.nure.bookhive.Models.DataTransferObjects.AuthorDTO;
import one.nure.bookhive.Models.DataTransferObjects.AuthorShortDTO;
import one.nure.bookhive.Models.DataTransferObjects.BookDTO;
import one.nure.bookhive.Models.DataTransferObjects.BookShortDTO;
import one.nure.bookhive.Models.Genre;
import one.nure.bookhive.Repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDTO> getAuthors() {
        return authorRepository.findAll().stream()
                .map(AuthorService::convertToAuthorDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthor(Long authorId) {
        return convertToAuthorDTO(authorRepository
                .findById(authorId).orElseThrow(() -> new RuntimeException("Author not found")));
    }

    public AuthorDTO createAuthor(Author author) {
        return convertToAuthorDTO(authorRepository.save(author));
    }

    public AuthorDTO updateAuthor(Long authorId, Author author) {
        Author existingAuthor = authorRepository.findById(authorId).orElseThrow(() ->
                new IllegalArgumentException("Author not found with id: " + author.getAuthor_id()));

        existingAuthor.setAuthorFullname(author.getAuthorFullname());
        existingAuthor.setBooks(author.getBooks());

        return convertToAuthorDTO(authorRepository.save(existingAuthor));
    }

    public static AuthorDTO convertToAuthorDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setAuthorId(author.getAuthor_id());
        dto.setAuthorFullname(author.getAuthorFullname());

        Set<BookShortDTO> books = author.getBooks().stream()
                .map(book -> new BookShortDTO(book.getBookId(), book.getTitle(), book.getPublishing_year(), book.getRating()))
                .collect(Collectors.toSet());

        dto.setBooks(books);

        return dto;
    }
}
