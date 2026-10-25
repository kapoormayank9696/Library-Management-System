package com.library.Library_Management_System.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AuthorRequestDTO {
    @NotBlank(message = "Author Name must be required")
    @Size(
            max = 255,
            message = "Author name can n't be exceed 255 characters"
    )
    private String authorName;
}
