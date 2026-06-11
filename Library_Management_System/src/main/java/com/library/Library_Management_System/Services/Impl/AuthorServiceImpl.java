package com.library.Library_Management_System.Services.Impl;

import com.library.Library_Management_System.Entity.Author;
import com.library.Library_Management_System.Repository.AuthorRepository;
import com.library.Library_Management_System.Services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorService authorService, AuthorRepository authorRepository) {
        this.authorService = authorService;
        this.authorRepository = authorRepository;
    }

    @Override
    public Author authorSaver(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(int id) {
        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found!"));
    }

    @Override
    public void deleteById(int id) {
        authorRepository.deleteById(id);
    }
}
