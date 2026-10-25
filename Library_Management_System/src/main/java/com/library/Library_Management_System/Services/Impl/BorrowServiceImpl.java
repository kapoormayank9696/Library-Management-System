
package com.library.Library_Management_System.Services.Impl;

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

    // ---------------- ADD BORROW BOOK ---------------
    @Override
    @Transactional
    public Borrow borrowSaver(int userId, int bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new RuntimeException("Book not found with id: " + bookId));

        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book is not available currently");
        }

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setReturnDate(null);

        return borrowRepository.save(borrow);
    }

    // ---------------- RETURN BOOK -------------------
    @Override
    @Transactional
    public Borrow returnBook(int borrowId) {

        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Borrow record not found with id: " + borrowId));

        // Prevent returning the same book more than once
        if (borrow.getReturnDate() != null) {
            throw new RuntimeException("Book has already been returned");
        }

        Book book = borrow.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        borrow.setReturnDate(LocalDate.now());

        return borrowRepository.save(borrow);
    }

    // ---------------- GET ALL BORROWS ---------------
    @Override
    public List<Borrow> getAllDetails() {
        return borrowRepository.findAll();
    }

    // ---------------- GET BY ID ----------------
    @Override
    public Borrow getById(int id) {
        return borrowRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Borrow not found with id: " + id));
    }

    // ---------------- UPDATE BORROW -----------------
    @Override
    @Transactional
    public Borrow updateBorrow(int id, Borrow updatedBorrow) {

        Borrow existingBorrow = borrowRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Borrow not found with id: " + id));

        // Do not allow changing an existing borrow record's
        // user, book, or dates directly.
        // Use returnBook() to return a book safely.
        throw new UnsupportedOperationException(
                "Borrow records cannot be edited directly. Use the return book operation instead."
        );
    }

    // ---------------- DELETE BORROW BY ID------------
    @Override
    @Transactional
    public void deleteById(int id) {

        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Borrow not found with id: " + id));

        // If the book is still borrowed, restore its stock
        if (borrow.getReturnDate() == null) {
            Book book = borrow.getBook();
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
        }

        borrowRepository.delete(borrow);
    }
}
