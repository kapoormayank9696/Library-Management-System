package com.library.Library_Management_System.Services.Impl;

import com.library.Library_Management_System.Entity.Borrow;
import com.library.Library_Management_System.Repository.BookRepository;
import com.library.Library_Management_System.Repository.BorrowRepository;
import com.library.Library_Management_System.Services.BorrowService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {
    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;

    public BorrowServiceImpl(BorrowRepository borrowRepository, BookRepository bookRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Borrow borrowSaver(int userId, int bookId) {
        Borrow borrow = new Borrow();
        return borrowRepository.save(borrow);
    }

    @Override
    public List<Borrow> getAllDetails() {
        return borrowRepository.findAll();
    }

    @Override
    public Borrow getById(int id) {
        return borrowRepository.getById(id);
    }

    @Override
    public void deleteById(int id) {
        borrowRepository.deleteById(id);
    }
}
