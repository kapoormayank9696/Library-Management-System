package com.library.Library_Management_System.DTO;

public class BorrowRequestDTO {
    private Integer userId;
    private Integer bookId;

    // Setter
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    // Getter
    public Integer getUserId() {
        return userId;
    }
    public Integer getBookId() {
        return bookId;
    }

}