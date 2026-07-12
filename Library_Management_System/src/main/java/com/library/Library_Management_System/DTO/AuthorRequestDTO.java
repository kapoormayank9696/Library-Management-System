package com.library.Library_Management_System.DTO;

import jakarta.validation.constraints.NotBlank;

public class AuthorRequestDTO {
    @NotBlank(message = "Author name is required")

    // Default Constructor
    public AuthorRequestDTO() {}

    // Parameterized Constructor
    public AuthorRequestDTO(String authorName) {
        this.authorName = authorName;
    }

    private String authorName;

    // Setter
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    // Getter
    public String getAuthorName() {
        return authorName;
    }
}
