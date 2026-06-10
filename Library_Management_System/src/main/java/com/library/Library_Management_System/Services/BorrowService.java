package com.library.Library_Management_System.Services;

import com.library.Library_Management_System.Entity.Borrow;

import java.util.List;

public interface BorrowService {
    Borrow borrowSaver(Long userId, Long bookId);
    List<Borrow> getAllDetails();
    Borrow getById(Long id);
    void deleteById(Long id);
}
