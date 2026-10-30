
package com.library.Library_Management_System.Controller;

import com.library.Library_Management_System.Entity.Author;
import com.library.Library_Management_System.Services.AuthorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AuthorPageController {

    private final AuthorService authorService;

    // Parameterized Constructor
    public AuthorPageController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Show all authors
    @GetMapping("/authors")
    public String showAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthor());
        return "authors";
    }

    // Show add author form
    @GetMapping("/authors/new")
    public String showAddAuthorForm() {
        return "add-author";
    }

    // Save author
    @PostMapping("/authors/new")
    public String saveAuthor(
            @RequestParam String authorName,
            RedirectAttributes redirectAttributes) {

        Author author = new Author();
        author.setAuthorName(authorName.trim());

        authorService.saveAll(List.of(author));

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Author added successfully!"
        );

        return "redirect:/authors";
    }

    // Delete author
    @PostMapping("/authors/delete/{id}")
    public String deleteAuthor(
            @PathVariable int id,
            RedirectAttributes redirectAttributes) {

        try {
            authorService.deleteById(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Author deleted successfully!"
            );

        } catch (RuntimeException e) {

            throw new RuntimeException(
                    "Cannot delete this author. Please reassign or remove all associated books first."
            );
        }

        return "redirect:/authors";
    }
}
