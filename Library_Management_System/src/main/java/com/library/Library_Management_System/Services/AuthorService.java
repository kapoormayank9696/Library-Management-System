package com.library.Library_Management_System.Services;

import com.library.Library_Management_System.Entity.Author;

import java.util.List;

public interface AuthorService {
    List<Author> saveAll(List<Author> author);
    List<Author> getAllAuthor();
    Author getAuthorById(int id);
    Author updateAuthor(int id, Author author);
    void deleteById(int id);
}
