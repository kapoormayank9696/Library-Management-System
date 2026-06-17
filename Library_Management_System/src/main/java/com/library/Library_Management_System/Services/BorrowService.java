package com.library.Library_Management_System.Services;

import com.library.Library_Management_System.Entity.Borrow;

import java.util.List;

public interface BorrowService {
    Borrow borrowSaver(int userId, int bookId);
    Borrow returnBook(int borrowId);
    List<Borrow> getAllDetails();
    Borrow getById(int id);
    void deleteById(int id);
}
