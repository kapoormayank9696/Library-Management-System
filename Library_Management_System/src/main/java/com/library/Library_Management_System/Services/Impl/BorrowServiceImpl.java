package com.library.Library_Management_System.Services.Impl;

import com.library.Library_Management_System.Entity.Book;
import com.library.Library_Management_System.Entity.Borrow;
import com.library.Library_Management_System.Entity.User;
import com.library.Library_Management_System.Repository.BookRepository;
import com.library.Library_Management_System.Repository.BorrowRepository;
import com.library.Library_Management_System.Repository.UserRepository;
import com.library.Library_Management_System.Services.BorrowService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BorrowServiceImpl(BorrowRepository borrowRepository,
                             BookRepository bookRepository,
                             UserRepository userRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    // ---------------- BORROW BOOK ----------------
    @Override
    public Borrow borrowSaver(int userId, int bookId) {

        // 1. Validate User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // 2. Validate Book
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        // 3. Check availability
        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book is not available currently");
        }

        // 4. Reduce stock
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        // 5. Create borrow record
        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setReturnDate(null);

        return borrowRepository.save(borrow);
    }

    // ---------------- RETURN BOOK ----------------
    @Override
    public Borrow returnBook(int borrowId) {

        // 1. Find borrow record
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found with id: " + borrowId));

        // 2. Get book
        Book book = borrow.getBook();

        // 3. Increase stock
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        // 4. Set return date
        borrow.setReturnDate(LocalDate.now());

        return borrowRepository.save(borrow);
    }

    // ---------------- GET ALL BORROWS ----------------
    @Override
    public List<Borrow> getAllDetails() {
        return borrowRepository.findAll();
    }

    // ---------------- GET BY ID ----------------
    @Override
    public Borrow getById(int id) {
        return borrowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrow not found with id: " + id));
    }

    // ---------------- DELETE ----------------
    @Override
    public void deleteById(int id) {
        borrowRepository.deleteById(id);
    }
}