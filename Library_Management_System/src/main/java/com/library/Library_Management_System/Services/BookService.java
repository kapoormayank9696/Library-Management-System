package com.library.Library_Management_System.Services;

import com.library.Library_Management_System.Entity.Book;

import java.util.List;

public interface BookService {
    Book bookSaver(Book book);
    List<Book> getAllBooks();
    Book getBookById(Long id);
    void deleteById(Long id);
}
