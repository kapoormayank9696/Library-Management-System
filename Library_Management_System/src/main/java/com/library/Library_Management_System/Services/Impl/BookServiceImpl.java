package com.library.Library_Management_System.Services.Impl;

import com.library.Library_Management_System.DTO.BookRequestDTO;
import com.library.Library_Management_System.Entity.Author;
import com.library.Library_Management_System.Entity.Book;
import com.library.Library_Management_System.Repository.BookRepository;
import com.library.Library_Management_System.Services.BookService;
import com.library.Library_Management_System.Repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book bookSaver(BookRequestDTO dto) {

        if (dto.getAuthorId() == null) {
            throw new RuntimeException("Author ID is required");
        }

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Book book = new Book();
        book.setBookName(dto.getBookName());
        book.setTitle(dto.getTitle());
        book.setPrice(dto.getPrice());
        book.setQuantity(dto.getQuantity());
        book.setAuthor(author);

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
