package com.library.Library_Management_System.Controller;

import com.library.Library_Management_System.DTO.BookRequestDTO;
import com.library.Library_Management_System.Entity.Book;
import com.library.Library_Management_System.Services.BookService;
import jakarta.validation.Valid;
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
    public List<Book> saveAll(@RequestBody List<BookRequestDTO> dto) {
        return bookService.saveAll(dto);
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
    public Book updateBookById(@PathVariable Integer id,@Valid @RequestBody BookRequestDTO book) {
        return bookService.updateBook(id,book);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Integer id) {
        bookService.deleteById(id);
    }
}
