package com.library.controller;

import com.library.model.Member;
import com.library.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class managing all member-related operations.
 * Handles the business logic for member management including CRUD operations.
 * 
 * @author 22ENG 143,149,50
 */
public class MemberController {
    
    /**
     * Adds a new member to the system.
     *
     * @param member The Member object containing member details
     * @return true if successfully added, false otherwise
     */
    public boolean addMember(Member member) {
        String sql = "INSERT INTO members (MemberNo, FirstName, LastName, PhoneNo) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, member.getMemberNo());
            pstmt.setString(2, member.getFirstName());
            pstmt.setString(3, member.getLastName());
            pstmt.setString(4, member.getPhoneNo());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // Get the auto-generated ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        member.setId(generatedKeys.getInt(1));
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
     * Retrieves all members from the database.
     *
     * @return List of all registered members
     */
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Member member = new Member(
                    rs.getString("MemberNo"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("PhoneNo")
                );
                member.setId(rs.getInt("ID"));
                members.add(member);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    
    /**
     * Update existing member in the database
     * @param member Member to update
     * @return true if successful, false otherwise
     */
    public boolean updateMember(Member member) {
        String sql = "UPDATE members SET MemberNo=?, FirstName=?, LastName=?, PhoneNo=? WHERE ID=?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, member.getMemberNo());
            pstmt.setString(2, member.getFirstName());
            pstmt.setString(3, member.getLastName());
            pstmt.setString(4, member.getPhoneNo());
            pstmt.setInt(5, member.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Find a member by their member number
     * @param memberNo Member number to search for
     * @return Member if found, null otherwise
     */
    public Member findByMemberNo(String memberNo) {
        String sql = "SELECT * FROM members WHERE MemberNo = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, memberNo);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Member member = new Member(
                    rs.getString("MemberNo"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("PhoneNo")
                );
                member.setId(rs.getInt("ID"));
                return member;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}