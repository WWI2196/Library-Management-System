package com.library.model;

import java.sql.Date;

/**
 * Represents a lending transaction in the library management system.
 * This class maintains the relationship between a book, member and the return date
 * for each lending transaction.
 * @author 22ENG 143,149,50
 */
public class Lending {
    /** Unique identifier for the lending transaction */
    private int id;
    
    /** The book being lent */
    private Book book;
    
    /** The member borrowing the book */
    private Member member;
    
    /** The expected return date */
    private Date returnDate;

    /**
     * Constructs a new Lending instance.
     *
     * @param book The book being lent
     * @param member The member borrowing the book
     * @param returnDate The expected return date
     */
    public Lending(Book book, Member member, Date returnDate) {
        this.book = book;
        this.member = member;
        this.returnDate = returnDate;
    }

    /**
     * Gets the lending transaction ID.
     * @return The lending ID
     */
    public int getId() { return id; }

    /**
     * Sets the lending transaction ID.
     * @param id The ID to set
     */
    public void setId(int id) { this.id = id; }
    
    /**
     * Gets the book being lent.
     * @return The book
     */
    public Book getBook() { return book; }

    /**
     * Sets the book being lent.
     * @param book The book to set
     */
    public void setBook(Book book) { this.book = book; }
    
    /**
     * Gets the member borrowing the book.
     * @return The member
     */
    public Member getMember() { return member; }

    /**
     * Sets the member borrowing the book.
     * @param member The member to set
     */
    public void setMember(Member member) { this.member = member; }
    
    /**
     * Gets the expected return date.
     * @return The return date
     */
    public Date getReturnDate() { return returnDate; }

    /**
     * Sets the expected return date.
     * @param returnDate The return date to set
     */
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
}