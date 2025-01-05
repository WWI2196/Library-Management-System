package com.library.librarymanagementsystem;

import com.library.controller.BookController;
import com.library.model.Book;
import java.util.List;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        BookController bookController = new BookController();
        
        // Test getting all books
        System.out.println("All books in library:");
        List<Book> books = bookController.getAllBooks();
        for (Book book : books) {
            System.out.println(book.getBookNo() + " - " + 
                             book.getTitle() + " by " + 
                             book.getAuthor() + 
                             (book.isAvailable() ? " (Available)" : " (Not Available)"));
        }
        
        // Test finding a specific book
        System.out.println("\nFinding book N123:");
        Book foundBook = bookController.findByBookNo("N123");
        if (foundBook != null) {
            System.out.println("Found: " + foundBook.getTitle() + " by " + foundBook.getAuthor());
        }
    }
}