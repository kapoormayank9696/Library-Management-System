package com.library.Library_Management_System.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BookRequestDTO {
    @NotBlank(message = "Book name is required")
    @Size (
            min = 5,
            max = 255,
            message = "Book name must be between 5 to 255 characters"
    )
    private String bookName;

    @NotBlank(message = "Title is required")
    @Size (
            min = 2,
            max = 255,
            message = "Title must be between 2 to 255 characters"
    )
    private String title;

    @NotNull(message = "Price must be required")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "Price must be greater than zero"
    )
    private BigDecimal price;

    @NotNull(message = "Author Id must be required")
    private Integer authorId;

    @PositiveOrZero(message = "Quantity cannot be negative")
    private int quantity;
}


