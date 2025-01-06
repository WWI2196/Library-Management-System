package com.library.controller;

import com.library.model.Book;
import com.library.model.Lending;
import com.library.model.Member;
import com.library.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class managing all lending-related operations.
 * Handles the business logic for book lending, returns, and lending history.
 * 
 * @author 22ENG 143,149,50
 * @version 1.0
 */
public class LendingController {
    /** Book controller for book-related operations */
    private BookController bookController;
    
    /** Member controller for member-related operations */
    private MemberController memberController;
    
    /**
     * Constructs a new LendingController instance.
     * Initializes required sub-controllers.
     */
    public LendingController() {
        this.bookController = new BookController();
        this.memberController = new MemberController();
    }
    
    /**
     * Issues a book to a member.
     *
     * @param bookNo The book number to be issued
     * @param memberNo The member number receiving the book
     * @param returnDate The expected return date
     * @return true if the book was successfully issued, false otherwise
     */
    public boolean issueBbook(String bookNo, String memberNo, Date returnDate) {
        Book book = bookController.findByBookNo(bookNo);
        Member member = memberController.findByMemberNo(memberNo);
        
        if (book == null || member == null || !book.isAvailable()) {
            return false;
        }
        
        String sql = "INSERT INTO lending (BookID, MemberID, ReturnDate) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, book.getId());
            pstmt.setInt(2, member.getId());
            pstmt.setDate(3, returnDate);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // Get the auto-generated ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Update book availability
                        book.setAvailable(false);
                        bookController.updateBook(book);
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
     * Get all current lendings
     * @return List of all lending records
     */
    public List<Lending> getAllLendings() {
        List<Lending> lendings = new ArrayList<>();
        String sql = "SELECT l.*, b.BookNo, b.Title, b.Author, " +
                    "m.MemberNo, m.FirstName, m.LastName " +
                    "FROM lending l " +
                    "JOIN books b ON l.BookID = b.ID " +
                    "JOIN members m ON l.MemberID = m.ID";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Book book = new Book(
                    rs.getString("BookNo"),
                    rs.getString("Title"),
                    rs.getString("Author")
                );
                book.setId(rs.getInt("BookID"));
                
                Member member = new Member(
                    rs.getString("MemberNo"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    null
                );
                member.setId(rs.getInt("MemberID"));
                
                Lending lending = new Lending(book, member, rs.getDate("ReturnDate"));
                lending.setId(rs.getInt("ID"));
                
                lendings.add(lending);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lendings;
    }
    
    /**
     * Check if a book is currently lent out
     * @param bookNo Book number to check
     * @return Lending record if book is lent, null otherwise
     */
    public Lending getCurrentLending(String bookNo) {
        String sql = "SELECT l.*, b.BookNo, b.Title, b.Author, " +
                    "m.MemberNo, m.FirstName, m.LastName " +
                    "FROM lending l " +
                    "JOIN books b ON l.BookID = b.ID " +
                    "JOIN members m ON l.MemberID = m.ID " +
                    "WHERE b.BookNo = ? AND b.Available = FALSE";
        
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
                book.setId(rs.getInt("BookID"));
                
                Member member = new Member(
                    rs.getString("MemberNo"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    null
                );
                member.setId(rs.getInt("MemberID"));
                
                Lending lending = new Lending(book, member, rs.getDate("ReturnDate"));
                lending.setId(rs.getInt("ID"));
                
                return lending;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Return a book
     * @param bookNo Book number to return
     * @return true if successful, false otherwise
     */
    public boolean returnBook(String bookNo) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            // Start transaction
            conn.setAutoCommit(false);
            try {
                // First make the book available
                String updateBookSql = "UPDATE books SET Available = TRUE WHERE BookNo = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateBookSql)) {
                    pstmt.setString(1, bookNo);
                    pstmt.executeUpdate();
                }
                
                // Then delete the lending record
                String deleteLendingSql = 
                    "DELETE FROM lending " +
                    "WHERE BookID = (SELECT ID FROM books WHERE BookNo = ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteLendingSql)) {
                    pstmt.setString(1, bookNo);
                    pstmt.executeUpdate();
                }
                
                conn.commit();
                return true;
                
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}