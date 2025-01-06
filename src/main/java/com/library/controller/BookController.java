package com.library.controller;

import com.library.model.Book;
import com.library.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for Book-related operations
 * @author Your Name
 */
public class BookController {
    
    /**
     * Add a new book to the database
     * @param book Book to add
     * @return true if successful, false otherwise
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
     * Get all books from the database
     * @return List of all books
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
     * Update existing book in the database
     * @param book Book to update
     * @return true if successful, false otherwise
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
     * Find a book by its book number
     * @param bookNo Book number to search for
     * @return Book if found, null otherwise
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
     * Set book availability status
     * @param bookNo Book number to update
     * @param available New availability status
     * @return true if successful, false otherwise
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
     * Check if a book is currently lent out
     * @param bookNo Book number to check
     * @return true if book is lent out, false otherwise
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