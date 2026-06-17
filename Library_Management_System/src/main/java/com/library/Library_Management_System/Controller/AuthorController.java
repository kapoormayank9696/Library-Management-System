package com.library.Library_Management_System.Controller;

import com.library.Library_Management_System.Entity.Author;
import com.library.Library_Management_System.Services.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public Author addAuthor(@RequestBody Author author) {
        return authorService.authorSaver(author);
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthor();
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable int id) {
        return authorService.getAuthorById(id);
    }

    @DeleteMapping("/{delete/id}")
    public void deleteById(@PathVariable int id) {
        authorService.deleteById(id);
    }
}
