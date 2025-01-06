package com.library.model;

import java.sql.Date;

/**
 * Represents a book lending record
 * @author 22ENG 143,149,50
 */
public class Lending {
    private int id;
    private Book book;
    private Member member;
    private Date returnDate;

    public Lending(Book book, Member member, Date returnDate) {
        this.book = book;
        this.member = member;
        this.returnDate = returnDate;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
}