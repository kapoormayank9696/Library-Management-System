package com.library.Library_Management_System.DTO;

public class UserRequestDTO {
    private String userName;
    private String email;

    // Default Constructor
    public UserRequestDTO() {}

    // Parameterized Constructor
    public UserRequestDTO(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    // Setter
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter
    public String getUserName(String userName) {
        return userName;
    }
    public String getEmail(String email) {
        return email;
    }
}
