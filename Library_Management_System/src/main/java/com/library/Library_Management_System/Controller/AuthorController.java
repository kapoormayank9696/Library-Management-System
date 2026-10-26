package com.library.Library_Management_System.Controller;

import com.library.Library_Management_System.Entity.Author;
import com.library.Library_Management_System.Services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Author>> saveAll(@RequestBody List<Author> authors) {
        List<Author> savedAuthors = authorService.saveAll(authors);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedAuthors);
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthor();
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable Integer id) {
        return authorService.getAuthorById(id);
    }

    @PutMapping("/update/{id}")
    public Author updateById(@PathVariable Integer id, @RequestBody Author author) {
        return authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Integer id) {
        authorService.deleteById(id);
    }
}

