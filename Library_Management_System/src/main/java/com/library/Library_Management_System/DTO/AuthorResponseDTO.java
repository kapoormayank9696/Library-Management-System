package com.library.Library_Management_System.DTO;

public class AuthorResponseDTO {
    private String authorName;

    // Default Constructor
    public AuthorResponseDTO() {}

    // Parameterized Constructor
    public AuthorResponseDTO(String authorName) {
        this.authorName = authorName;
    }

    // Getter
    public String getAuthorName() {
        return authorName;
    }
}
