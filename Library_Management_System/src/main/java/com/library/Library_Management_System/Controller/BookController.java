package com.library.Library_Management_System.Controller;

import com.library.Library_Management_System.Entity.Book;
import com.library.Library_Management_System.Services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {
    private final BookService bookService;
    private BookController bookController;

    public BookController(BookController bookController, BookService bookService) {
        this.bookController = bookController;
        this.bookService = bookService;
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.bookSaver(book);
    }

    @GetMapping
    public List<Book> getAllBook() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(int id) {
        return bookService.getBookById(id);
    }

    @DeleteMapping("/{delete/id}")
    public void deleteBook(int id) {
        bookService.deleteById(id);
    }
}
