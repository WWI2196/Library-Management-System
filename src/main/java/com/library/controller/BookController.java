package com.library.controller;

import com.library.model.Book;
import com.library.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class handling all book-related operations in the library management system.
 * This class provides methods for CRUD operations on books and manages book availability.
 * 
 * @author 22ENG 143,149,50
 */
public class BookController {
    
    /**
     * Adds a new book to the library database.
     *
     * @param book The Book object containing the book details
     * @return true if the book was successfully added, false otherwise
     */
    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (BookNo, Title, Author, Available) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, book.getBookNo());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setBoolean(4, book.isAvailable());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // Get the auto-generated ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        book.setId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Retrieves all books from the database.
     *
     * @return List of all books in the library
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Book book = new Book(
                    rs.getString("BookNo"),
                    rs.getString("Title"),
                    rs.getString("Author")
                );
                book.setId(rs.getInt("ID"));
                book.setAvailable(rs.getBoolean("Available"));
                books.add(book);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    /**
     * Updates the details of an existing book in the database.
     *
     * @param book The Book object containing updated details
     * @return true if the book was successfully updated, false otherwise
     */
    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET BookNo=?, Title=?, Author=?, Available=? WHERE ID=?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, book.getBookNo());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setBoolean(4, book.isAvailable());
            pstmt.setInt(5, book.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Searches for a book by its book number.
     *
     * @param bookNo The book number to search for
     * @return The Book object if found, null otherwise
     */
    public Book findByBookNo(String bookNo) {
        String sql = "SELECT * FROM books WHERE BookNo = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bookNo);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Book book = new Book(
                    rs.getString("BookNo"),
                    rs.getString("Title"),
                    rs.getString("Author")
                );
                book.setId(rs.getInt("ID"));
                book.setAvailable(rs.getBoolean("Available"));
                return book;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Updates the availability status of a book.
     *
     * @param bookNo The book number to update
     * @param available The new availability status
     * @return true if the status was successfully updated, false otherwise
     */
    public boolean setBookAvailability(String bookNo, boolean available) {
        String sql = "UPDATE books SET Available = ? WHERE BookNo = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBoolean(1, available);
            pstmt.setString(2, bookNo);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Checks if a book is currently lent out.
     *
     * @param bookNo The book number to check
     * @return true if the book is currently lent out, false otherwise
     */
    public boolean isBookLent(String bookNo) {
        String sql = "SELECT COUNT(*) FROM lending l " +
                    "JOIN books b ON l.BookID = b.ID " +
                    "WHERE b.BookNo = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bookNo);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}