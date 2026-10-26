
package com.library.Library_Management_System.Services.Impl;

import com.library.Library_Management_System.Entity.Author;
import com.library.Library_Management_System.Repository.AuthorRepository;
import com.library.Library_Management_System.Services.AuthorService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    // Private Access Specifier And Data Members
    private final AuthorRepository authorRepository;

    // Parameterized Constructor
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    // ---------------- ADD USERS ---------------------
    @Override
    public List<Author> saveAll(List<Author> authors) {
        return authorRepository.saveAll(authors);
    }

    // ---------------- GET ALL USERS ----------------
    @Override
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    // ---------------- GET USER BY ID ----------------
    @Override
    public Author getAuthorById(int id) {
        return authorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Author not found with ID: " + id));
    }

    // ---------------- UPDATE USER BY ID--------------
    @Override
    public Author updateAuthor(int id, Author author) {

        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Author not found with ID: " + id));

        // Update author details
        existingAuthor.setAuthorName(author.getAuthorName());

        return authorRepository.save(existingAuthor);
    }

    // ---------------- DELETE USER BY ID--------------
    @Override
    public void deleteById(int id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author not found with ID: " + id);
        }

        authorRepository.deleteById(id);
    }
}
