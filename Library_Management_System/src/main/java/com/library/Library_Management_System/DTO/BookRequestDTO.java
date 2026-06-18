package com.library.Library_Management_System.DTO;

import java.math.BigDecimal;

public class BookRequest {
    private String bookName;
    private String title;
    private BigDecimal price;
    private Integer authorId;
    private int quantity;

    public BookRequest(String bookName,String title, BigDecimal price, Integer authorId,int quantity) {
        this.bookName = bookName;
        this.title = title;
        this.price = price;
        this.authorId = authorId;
        this.quantity = quantity;
    }

    // Setter
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter
    public String getBookName() {
        return bookName;
    }
    public String getTitle() {
        return title;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public Integer getAuthorId() {
        return authorId;
    }
    public int getQuantity() {
        return quantity;
    }
}
