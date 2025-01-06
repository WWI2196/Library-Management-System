package com.library.model;

/**
 * Represents a book in the library system.
 * 
 * @author 22ENG 143,149,50
 * @version 1.0
 */
public class Book {
    private int id;
    private String bookNo;
    private String title;
    private String author;
    private boolean available;

    /**
     * Creates a new Book instance.
     * 
     * @param bookNo unique book ID 
     * @param title title of the book
     * @param author author of the book
     */
    public Book(String bookNo, String title, String author) {
        this.bookNo = bookNo;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getBookNo() { return bookNo; }
    public void setBookNo(String bookNo) { this.bookNo = bookNo; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}