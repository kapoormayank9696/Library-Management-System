
package com.library.Library_Management_System.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequestDTO {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be greater than zero")
    private Integer userId;

    @NotNull(message = "Book ID is required")
    @Positive(message = "Book ID must be greater than zero")
    private Integer bookId;
}
