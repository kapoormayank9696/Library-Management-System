package com.library.Library_Management_System.Services;

import com.library.Library_Management_System.Entity.Author;

import java.util.List;

public interface AuthorService {
    Author authorSaver(Author author);
    List<Author> getAllAuthor();
    Author getAuthorById(Long id);
    void deleteById(Long id);
}
