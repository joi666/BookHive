package one.nure.bookhive.Controllers;

import one.nure.bookhive.Models.Author;
import one.nure.bookhive.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v01/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(path = "/getAll")
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }

    @PostMapping(path = "/create")
    public Author addAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @PutMapping(path = "/update/{authorId}")
    public Author updateAuthor(@PathVariable Long authorId, @RequestBody Author author) {
        return authorService.updateAuthor(authorId, author);
    }
}
