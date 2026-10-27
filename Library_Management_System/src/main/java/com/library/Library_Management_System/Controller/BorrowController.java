
package com.library.Library_Management_System.Controller;

import com.library.Library_Management_System.DTO.BorrowRequestDTO;
import com.library.Library_Management_System.Entity.Borrow;
import com.library.Library_Management_System.Services.BorrowService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    // Add multiple borrow records
    @PostMapping
    public List<Borrow> saveAll(
            @RequestBody List<@Valid BorrowRequestDTO> request) {
        return borrowService.saveAll(request);
    }

    // Return a book
    @PutMapping("/return/{id}")
    public Borrow returnBookById(@PathVariable int id) {
        return borrowService.returnBook(id);
    }

    // Get all borrow records
    @GetMapping
    public List<Borrow> getAllBorrow() {
        return borrowService.getAllDetails();
    }

    // Get borrow record by ID
    @GetMapping("/{id}")
    public Borrow getById(@PathVariable int id) {
        return borrowService.getById(id);
    }

    // Update borrow record
    @PutMapping("/update/{id}")
    public Borrow updateBorrowById(
            @PathVariable int id,
            @RequestBody Borrow borrow) {
        return borrowService.updateBorrow(id, borrow);
    }

    // Delete borrow record
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable int id) {
        borrowService.deleteById(id);
    }
}
