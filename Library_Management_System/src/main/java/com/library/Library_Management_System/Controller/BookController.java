package com.library.Library_Management_System.Controller;

import com.library.Library_Management_System.DTO.BookRequestDTO;
import com.library.Library_Management_System.Entity.Book;
import com.library.Library_Management_System.Services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book saveAll(@RequestBody BookRequestDTO dto) {
        return bookService.saveBook(dto);
    }

    @GetMapping
    public List<Book> getAllBook() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/update/{id}")
    public Book updateBook(@PathVariable Integer id,@RequestBody BookRequestDTO book) {
        return bookService.updateBook(id,book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
