package com.library.Library_Management_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class BorrowResponseDTO {
    // Private Access Specifier And Data Members
    private Integer borrowId;
    private String userName;
    private String bookName;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}