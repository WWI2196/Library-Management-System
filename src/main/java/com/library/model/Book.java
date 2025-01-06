package com.library.model;

/**
 * Represents a book entity in the library management system.
 * This class contains information about a book including its ID, book number,
 * title, author and availability status.
 * 
 * @author 22ENG 143,149,50
 */
public class Book {
    /** Unique identifier for the book in the database */
    private int id;
    
    /** Unique book number/code assigned by the library */
    private String bookNo;
    
    /** Title of the book */
    private String title;
    
    /** Author of the book */
    private String author;
    
    /** Flag indicating if the book is available for lending */
    private boolean available;

    /**
     * Constructs a new Book instance with the specified details.
     *
     * @param bookNo The unique book number/code
     * @param title The title of the book
     * @param author The author of the book
     */
    public Book(String bookNo, String title, String author) {
        this.bookNo = bookNo;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    /**
     * Gets the database ID of the book.
     * @return The book's database ID
     */
    public int getId() { return id; }

    /**
     * Sets the database ID of the book.
     * @param id The ID to set
     */
    public void setId(int id) { this.id = id; }
    
    /**
     * Gets the book number/code.
     * @return The book number
     */
    public String getBookNo() { return bookNo; }

    /**
     * Sets the book number/code.
     * @param bookNo The book number to set
     */
    public void setBookNo(String bookNo) { this.bookNo = bookNo; }
    
    /**
     * Gets the title of the book.
     * @return The title of the book
     */
    public String getTitle() { return title; }

    /**
     * Sets the title of the book.
     * @param title The title to set
     */
    public void setTitle(String title) { this.title = title; }
    
    /**
     * Gets the author of the book.
     * @return The author of the book
     */
    public String getAuthor() { return author; }

    /**
     * Sets the author of the book.
     * @param author The author to set
     */
    public void setAuthor(String author) { this.author = author; }
    
    /**
     * Checks if the book is available for lending.
     * @return True if the book is available, false otherwise
     */
    public boolean isAvailable() { return available; }

    /**
     * Sets the availability status of the book.
     * @param available The availability status to set
     */
    public void setAvailable(boolean available) { this.available = available; }
}