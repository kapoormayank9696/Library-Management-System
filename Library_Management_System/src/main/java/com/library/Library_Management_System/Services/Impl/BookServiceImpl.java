package com.library.Library_Management_System.Services.Impl;

import com.library.Library_Management_System.Entity.Book;
import com.library.Library_Management_System.Repository.BookRepository;
import com.library.Library_Management_System.Services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book bookSaver(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }
}
