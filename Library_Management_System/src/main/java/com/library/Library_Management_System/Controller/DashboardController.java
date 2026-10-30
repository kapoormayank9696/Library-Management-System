package com.library.Library_Management_System.Controller;

import com.library.Library_Management_System.Entity.Book;
import com.library.Library_Management_System.Services.BookService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final BookService bookService;

    public DashboardController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {

        List<Book> books = bookService.getAllBooks();

        long totalBooks = books.size();

        long totalQuantity = books.stream()
                .mapToLong(Book::getQuantity)
                .sum();

        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("issuedBooks", 0);
        model.addAttribute("totalMembers", 0);

        model.addAttribute(
                "recentBooks",
                books.stream().limit(5).toList()
        );

        return "dashboard";
    }
}