package com.library.Library_Management_System.DTO;

public class UserResponseDTO {
    private Integer userId;
    private String userName;
    private String email;

    public UserResponseDTO(Integer userId,String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }

    // Setter
    public void setUserId(Integer userId) {
     this.userId = userId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter
    public Integer getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public String getEmail() {
        return email;
    }
}
