
package com.library.Library_Management_System.Services.Impl;

import com.library.Library_Management_System.Entity.Author;
import com.library.Library_Management_System.Repository.AuthorRepository;
import com.library.Library_Management_System.Repository.BookRepository;
import com.library.Library_Management_System.Services.AuthorService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    // Data Members
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    // Parameterized Constructor
    public AuthorServiceImpl(AuthorRepository authorRepository,
                             BookRepository bookRepository) {

        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    // ---------------- ADD AUTHORS ----------------
    @Override
    public List<Author> saveAll(List<Author> authors) {
        return authorRepository.saveAll(authors);
    }

    // ---------------- GET ALL AUTHORS ----------------
    @Override
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    // ---------------- GET AUTHOR BY ID ----------------
    @Override
    public Author getAuthorById(int id) {
        return authorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Author not found with ID: " + id));
    }

    // ---------------- UPDATE AUTHOR BY ID ----------------
    @Override
    public Author updateAuthor(int id, Author author) {

        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Author not found with ID: " + id));

        existingAuthor.setAuthorName(author.getAuthorName());

        return authorRepository.save(existingAuthor);
    }

    // ---------------- DELETE AUTHOR BY ID ----------------
    @Override
    public void deleteById(int id) {

        // Check whether author exists
        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Author not found with ID: " + id));

        // Check whether author has associated books
        if (bookRepository.existsByAuthor_AuthorId(id)) {

            throw new RuntimeException(
                    "This author cannot be deleted because books are associated with this author."
            );
        }

        // Delete author if no books are associated
        authorRepository.delete(author);
    }
}
