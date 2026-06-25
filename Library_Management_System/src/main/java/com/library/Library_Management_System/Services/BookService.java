package com.library.Library_Management_System.Services;

import com.library.Library_Management_System.DTO.BookRequestDTO;
import com.library.Library_Management_System.Entity.Book;
import com.library.Library_Management_System.Repository.BookRepository;

import java.util.List;

public interface BookService {
    Book bookSaver(BookRequestDTO dto);
    List<Book> getAllBooks();
    Book getBookById(int id);
    void deleteById(int id);
}
