package com.library.model;

/**
 * Represents a library member
 * @author 22ENG 143,149,50
 */
public class Member {
    private int id;
    private String memberNo;
    private String firstName;
    private String lastName;
    private String phoneNo;

    public Member(String memberNo, String firstName, String lastName, String phoneNo) {
        this.memberNo = memberNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getMemberNo() { return memberNo; }
    public void setMemberNo(String memberNo) { this.memberNo = memberNo; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
}