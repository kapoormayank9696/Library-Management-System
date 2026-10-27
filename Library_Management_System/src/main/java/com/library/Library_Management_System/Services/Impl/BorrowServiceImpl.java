
package com.library.Library_Management_System.Services.Impl;

import com.library.Library_Management_System.DTO.BorrowRequestDTO;
import com.library.Library_Management_System.Entity.Book;
import com.library.Library_Management_System.Entity.Borrow;
import com.library.Library_Management_System.Entity.User;
import com.library.Library_Management_System.Repository.BookRepository;
import com.library.Library_Management_System.Repository.BorrowRepository;
import com.library.Library_Management_System.Repository.UserRepository;
import com.library.Library_Management_System.Services.BorrowService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BorrowServiceImpl(
            BorrowRepository borrowRepository,
            BookRepository bookRepository,
            UserRepository userRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    // ---------------- ADD MULTIPLE BORROW RECORDS ----------------
    @Override
    @Transactional
    public List<Borrow> saveAll(List<BorrowRequestDTO> requests) {

        List<Borrow> borrows = new ArrayList<>();

        for (BorrowRequestDTO request : requests) {

            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "User not found with id: " + request.getUserId()));

            Book book = bookRepository.findById(request.getBookId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Book not found with id: " + request.getBookId()));

            if (book.getQuantity() <= 0) {
                throw new RuntimeException(
                        "Book is not available: " + book.getBookName());
            }

            // Decrease available quantity
            book.setQuantity(book.getQuantity() - 1);
            bookRepository.save(book);

            Borrow borrow = new Borrow();
            borrow.setUser(user);
            borrow.setBook(book);
            borrow.setBorrowDate(LocalDate.now());
            borrow.setReturnDate(null);

            borrows.add(borrow);
        }

        return borrowRepository.saveAll(borrows);
    }

    // ---------------- RETURN BOOK ----------------
    @Override
    @Transactional
    public Borrow returnBook(int borrowId) {

        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Borrow record not found with id: " + borrowId));

        if (borrow.getReturnDate() != null) {
            throw new RuntimeException("Book has already been returned");
        }

        Book book = borrow.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        borrow.setReturnDate(LocalDate.now());

        return borrowRepository.save(borrow);
    }

    // ---------------- GET ALL BORROWS ----------------
    @Override
    public List<Borrow> getAllDetails() {
        return borrowRepository.findAll();
    }

    // ---------------- GET BORROW BY ID ----------------
    @Override
    public Borrow getById(int id) {
        return borrowRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Borrow not found with id: " + id));
    }

    // ---------------- UPDATE BORROW ----------------

    @Override
    @Transactional
    public Borrow updateBorrow(int id, Borrow updatedBorrow) {

        Borrow existingBorrow = borrowRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Borrow not found with id: " + id));

        if (updatedBorrow.getUser() == null ||
                updatedBorrow.getUser().getUserId() <= 0) {
            throw new RuntimeException("User ID is required");
        }

        if (updatedBorrow.getBook() == null ||
                updatedBorrow.getBook().getBookId() <= 0) {
            throw new RuntimeException("Book ID is required");
        }

        User newUser = userRepository.findById(
                        updatedBorrow.getUser().getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Book oldBook = existingBorrow.getBook();

        Book newBook = bookRepository.findById(
                        updatedBorrow.getBook().getBookId())
                .orElseThrow(() ->
                        new RuntimeException("Book not found"));

        boolean sameBook = oldBook.getBookId() == (newBook.getBookId());
        boolean alreadyReturned = existingBorrow.getReturnDate() != null;

        // Change book only when the new book is different
        if (!sameBook && !alreadyReturned) {

            if (newBook.getQuantity() <= 0) {
                throw new RuntimeException("New book is not available");
            }

            oldBook.setQuantity(oldBook.getQuantity() + 1);
            newBook.setQuantity(newBook.getQuantity() - 1);

            bookRepository.save(oldBook);
            bookRepository.save(newBook);
        }

        existingBorrow.setUser(newUser);
        existingBorrow.setBook(newBook);

        // Do not use updateBorrow to return a book.
        // Use the dedicated returnBook endpoint instead.
        if (updatedBorrow.getReturnDate() != null) {
            throw new RuntimeException(
                    "Use PUT /api/borrows/return/" + id
                            + " to return this book");
        }

        return borrowRepository.save(existingBorrow);
    }

    // ---------------- DELETE BORROW ----------------
    @Override
    @Transactional
    public void deleteById(int id) {

        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Borrow not found with id: " + id));

        // Restore quantity if the book has not been returned
        if (borrow.getReturnDate() == null) {
            Book book = borrow.getBook();
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
        }

        borrowRepository.delete(borrow);
    }
}
