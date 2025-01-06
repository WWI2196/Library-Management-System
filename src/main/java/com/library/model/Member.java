package com.library.model;

/**
 * Represents a library member
 * @author 22ENG 143,149,50
 */
public class Member {
    /** Unique database identifier for the member */
    private int id;
    
    /** Unique membership number */
    private String memberNo;
    
    /** Member's first name */
    private String firstName;
    
    /** Member's last name */
    private String lastName;
    
    /** Member's contact phone number */
    private String phoneNo;

    /**
     * Constructs a new Member instance with the specified details.
     *
     * @param memberNo The unique membership number
     * @param firstName The member's first name
     * @param lastName The member's last name
     * @param phoneNo The member's phone number
     */
    public Member(String memberNo, String firstName, String lastName, String phoneNo) {
        this.memberNo = memberNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
    }

     /**
     * Gets the member's database ID.
     * @return The database ID
     */
    public int getId() { return id; }

    /**
     * Sets the member's database ID.
     * @param id The ID to set
     */
    public void setId(int id) { this.id = id; }
    
    /**
     * Gets the member's unique membership number.
     * @return The membership number
     */
    public String getMemberNo() { return memberNo; }

    /**
     * Sets the member's unique membership number.
     * @param memberNo The membership number to set
     */
    public void setMemberNo(String memberNo) { this.memberNo = memberNo; }
    
    /**
     * Gets the member's first name.
     * @return The first name
     */
    public String getFirstName() { return firstName; }

    /**
     * Sets the member's first name.
     * @param firstName The first name to set
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    /**
     * Gets the member's last name.
     * @return The last name
     */
    public String getLastName() { return lastName; }

    /**
     * Sets the member's last name.
     * @param lastName The last name to set
     */
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    /**
     * Gets the member's contact phone number.
     * @return The phone number
     */
    public String getPhoneNo() { return phoneNo; }

    /**
     * Sets the member's contact phone number.
     * @param phoneNo The phone number to set
     */
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
}