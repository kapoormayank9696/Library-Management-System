
package com.library.Library_Management_System.Controller;

import com.library.Library_Management_System.DTO.BorrowRequestDTO;
import com.library.Library_Management_System.Entity.Borrow;
import com.library.Library_Management_System.Services.BorrowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    // Add multiple borrow records
    @PostMapping
    public Borrow saveBorrow(
            @RequestBody BorrowRequestDTO request) {
        return borrowService.saveBorrow(request);
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
    public Borrow updateBorrow(
            @PathVariable int id,
            @RequestBody Borrow borrow) {
        return borrowService.updateBorrow(id, borrow);
    }

    // Return borrow record
    @PutMapping("/{id}/return")
    public Borrow returnBook(@PathVariable Integer id,
                             @RequestBody(required = false) Map<String, String> body) {
        String d = (body != null) ? body.get("returnDate") : null;
        LocalDate date = (d != null && !d.isBlank()) ? LocalDate.parse(d) : null;
        return borrowService.returnBook(id, date);
    }

    // Delete borrow record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        borrowService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
