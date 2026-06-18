package com.library.Library_Management_System.DTO;

public class BookResponseDTO {
    private Integer bookId;
    private String bookName;
    private String title;
    private String authorName;
    private int quantity;

    public BookResponseDTO(Integer bookId,String bookName, String title, String authorName,int quantity) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.title = title;
        this.authorName = authorName;
        this.quantity = quantity;
    }

    // Setter
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter
    public Integer getBookId() {
        return bookId;
    }
    public String getBookName() {
        return bookName;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthorName() {
        return authorName;
    }
    public int getQuantity() {
        return quantity;
    }
}

