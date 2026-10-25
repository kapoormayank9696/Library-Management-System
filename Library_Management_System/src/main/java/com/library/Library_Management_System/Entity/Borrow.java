package com.library.Library_Management_System.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Borrow")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrow_id")
    private int borrowId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "borrow_Date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "return_Date", nullable = false)
    private LocalDate returnDate;
}
