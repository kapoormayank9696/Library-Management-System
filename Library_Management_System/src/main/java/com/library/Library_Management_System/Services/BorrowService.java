package com.library.Library_Management_System.Services;

import com.library.Library_Management_System.DTO.BorrowRequestDTO;
import com.library.Library_Management_System.Entity.Borrow;

import java.time.LocalDate;
import java.util.List;

public interface BorrowService {
    Borrow saveBorrow(BorrowRequestDTO dto);
    Borrow returnBook(int borrowId, LocalDate date);
    List<Borrow> getAllDetails();
    Borrow getById(int id);
    Borrow updateBorrow(int id, Borrow borrow);
    void deleteById(int id);
}
