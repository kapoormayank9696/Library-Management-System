package com.library.Library_Management_System.Services.Impl;

import com.library.Library_Management_System.Repository.BorrowRepository;
import org.springframework.stereotype.Service;

@Service
public class BorrowServiceImpl {
    private final BorrowRepository borrowRepository;

    public BorrowServiceImpl(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }
}
