
package com.library.Library_Management_System.Services.Impl;

import com.library.Library_Management_System.DTO.BookRequestDTO;
import com.library.Library_Management_System.Entity.Author;
import com.library.Library_Management_System.Entity.Book;
import com.library.Library_Management_System.Repository.AuthorRepository;
import com.library.Library_Management_System.Repository.BookRepository;
import com.library.Library_Management_System.Services.BookService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    // Private Access Specifier And Data Members
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    // Parameterized Constructor
    public BookServiceImpl(
            BookRepository bookRepository,
            AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // ---------------- ADD BOOK ----------------------
    @Override
    public List<Book> saveAll(List<BookRequestDTO> dts) {
        List<Book> books = new ArrayList<>();

        for(BookRequestDTO dto: dts) {

            if (dto.getAuthorId() == null) {
                throw new RuntimeException("Author ID is required");
            }

            Author author = authorRepository.findById(dto.getAuthorId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Author not found with ID: " + dto.getAuthorId()));
            Book book = new Book();
            book.setBookName(dto.getBookName());
            book.setTitle(dto.getTitle());
            book.setPrice(dto.getPrice());
            book.setQuantity(dto.getQuantity());
            book.setAuthor(author);

            books.add(book);
        }
       return bookRepository.saveAll(books);
    }

    // ---------------- GET ALL BOOKS -----------------
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // ---------------- GET BOOK BY ID ----------------
    @Override
    public Book getBookById(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Book not found with ID: " + id));
    }

    // ---------------- UPDATE BOOK BY ID--------------
    @Override
    public Book updateBook(int id, BookRequestDTO dto) {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Book not found with ID: " + id));

        if (dto.getAuthorId() == null) {
            throw new RuntimeException("Author ID is required");
        }

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Author not found with ID: " + dto.getAuthorId()));

        existingBook.setBookName(dto.getBookName());
        existingBook.setTitle(dto.getTitle());
        existingBook.setPrice(dto.getPrice());
        existingBook.setQuantity(dto.getQuantity());
        existingBook.setAuthor(author);

        return bookRepository.save(existingBook);
    }

    // ---------------- DELETE BOOK BY ID--------------
    @Override
    public void deleteById(int id) {

        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with ID: " + id);
        }

        bookRepository.deleteById(id);
    }
}
