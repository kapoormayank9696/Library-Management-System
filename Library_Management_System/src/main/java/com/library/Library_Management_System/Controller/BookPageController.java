
package com.library.Library_Management_System.Controller;

import com.library.Library_Management_System.DTO.BookRequestDTO;
import com.library.Library_Management_System.Repository.AuthorRepository;
import com.library.Library_Management_System.Services.BookService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookPageController {

    private final BookService bookService;
    private final AuthorRepository authorRepository;

    public BookPageController(
            BookService bookService,
            AuthorRepository authorRepository) {

        this.bookService = bookService;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/books")
    public String showBooks(Model model) {

        model.addAttribute("books", bookService.getAllBooks());

        return "books";
    }

    @GetMapping("/books/new")
    public String showAddBookForm(Model model) {

        model.addAttribute("book", new BookRequestDTO());
        model.addAttribute("authors", authorRepository.findAll());

        return "add-book";
    }

    @PostMapping("/books")
    public String saveBook(
            @Valid @ModelAttribute("book") BookRequestDTO book,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("authors", authorRepository.findAll());
            return "add-book";
        }

        bookService.saveAll(List.of(book));

        return "redirect:/books";
    }


    @PostMapping("/books/delete/{id}")
    public String deleteBook(
            @PathVariable int id,
            RedirectAttributes redirectAttributes) {

        try {
            bookService.deleteById(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage", "Book deleted successfully!"
            );

        } catch (RuntimeException exception) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage", exception.getMessage()
            );
        }

        return "redirect:/books";
    }

}
