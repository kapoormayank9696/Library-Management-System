package com.library.Library_Management_System.DTO;

import java.time.LocalDate;

public class BorrowResponseDTO {
    private Integer borrowId;
    private String userName;
    private String bookName;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowResponseDTO(Integer borrowId, String userName, String bookName, LocalDate borrowDate, LocalDate returnDate) {
        this.borrowId = borrowId;
        this.userName = userName;
        this.bookName = bookName;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // Setter
    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    // Getter
    public Integer getBorrowId() {
        return borrowId;
    }
    public String getUserName() {
        return  userName;
    }
    public String getBookName() {
        return bookName;
    }
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
}