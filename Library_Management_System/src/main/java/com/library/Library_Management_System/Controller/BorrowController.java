package com.library.Library_Management_System.Controller;

import com.library.Library_Management_System.DTO.BorrowRequestDTO;
import com.library.Library_Management_System.Entity.Borrow;
import com.library.Library_Management_System.Services.BorrowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping
    public Borrow addBorrow(@RequestBody BorrowRequestDTO request) {
        return borrowService.borrowSaver(request.getUserId(),request.getBookId());
    }

    @GetMapping("/return/{id}")
    public Borrow returnBookById(@PathVariable Integer id) {
        return borrowService.returnBook(id);
    }

    @GetMapping
    public List<Borrow> getAllBorrow() {
        return borrowService.getAllDetails();
    }

    @GetMapping("/{id}")
    public Borrow getById(@PathVariable Integer id) {
        return borrowService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Integer id) {
        borrowService.deleteById(id);
    }
}
