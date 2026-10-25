package com.library.Library_Management_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class BookResponseDTO {
    // Private Access Specifier And Data Members
    private Integer bookId;
    private String bookName;
    private String title;
    private String authorName;
    private BigDecimal price;
    private int quantity;
}

