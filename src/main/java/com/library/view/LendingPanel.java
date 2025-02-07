package com.library.view;

import com.library.controller.LendingController;
import com.library.model.Lending;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;
import java.util.Calendar;

/**
 * Panel for managing book lending operations.
 * Provides interface for issuing and returning books, and viewing current lendings.
 * @author 22ENG 143,149,50
 */
public class LendingPanel extends JPanel {
     /** Controller for lending operations */
    private LendingController controller;
    
    /** Table displaying lending information */
    private JTable lendingTable;
    
    /** Model for the lending table */
    private DefaultTableModel tableModel;
    
    /** Text fields for book and member information */
    private JTextField txtBookNo, txtMemberNo;
    
    /** Buttons for lending operations */
    private JButton btnIssue, btnReturn, btnClear;
    
    /** Reference to the book panel for refreshing book status */
    private BookPanel bookPanel;
    
    /**
     * Constructs a new LendingPanel.
     * Initializes the controller and UI components.
     *
     * @param bookPanel Reference to the book panel for updates
     */
    public LendingPanel(BookPanel bookPanel) {
        this.bookPanel = bookPanel;
        controller = new LendingController();
        initComponents();
        loadLendings();
    }
    
    /**
     * Initializes and arranges all GUI components.
     * Sets up the input fields, buttons, and lending table.
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Create input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Add input fields
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Book No:"), gbc);
        gbc.gridx = 1;
        txtBookNo = new JTextField(20);
        inputPanel.add(txtBookNo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Member No:"), gbc);
        gbc.gridx = 1;
        txtMemberNo = new JTextField(20);
        inputPanel.add(txtMemberNo, gbc);
        
        // Add buttons
        JPanel buttonPanel = new JPanel();
        btnIssue = new JButton("Issue Book");
        btnReturn = new JButton("Return Book");
        btnClear = new JButton("Clear");
        
        buttonPanel.add(btnIssue);
        buttonPanel.add(btnReturn); 
        buttonPanel.add(btnClear);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);
        
        // Create table
        String[] columns = {"Book No", "Book Title", "Member No", "Member Name", "Return Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        lendingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(lendingTable);
        
        // Add components to panel
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add button listeners
        btnIssue.addActionListener(e -> issueBook());
        btnReturn.addActionListener(e -> returnBook());
        btnClear.addActionListener(e -> clearFields());
    }
    
    /**
     * Loads all current lendings from the database and displays them in the table.
     */
    private void loadLendings() {
        tableModel.setRowCount(0);
        List<Lending> lendings = controller.getAllLendings();
        for (Lending lending : lendings) {
            Object[] row = {
                lending.getBook().getBookNo(),
                lending.getBook().getTitle(),
                lending.getMember().getMemberNo(),
                lending.getMember().getFirstName() + " " + lending.getMember().getLastName(),
                lending.getReturnDate()
            };
            tableModel.addRow(row);
        }
    }
    
    /**
     * Issues a book to a member.
     * Validates input and shows appropriate messages.
     * Sets return date to 14 days from current date.
     */
    private void issueBook() {
        String bookNo = txtBookNo.getText().trim();
        String memberNo = txtMemberNo.getText().trim();
        
        if (bookNo.isEmpty() || memberNo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Set return date to 14 days from now
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 14);
        Date returnDate = new Date(cal.getTimeInMillis());
        
        if (controller.issueBbook(bookNo, memberNo, returnDate)) {
            loadLendings();
            clearFields();
            bookPanel.refreshTable();
            JOptionPane.showMessageDialog(this,
                "Book issued successfully",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Failed to issue book. Please check if:\n" +
                "1. Book exists and is available\n" +
                "2. Member exists\n" +
                "3. Book is not already issued",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Returns a borrowed book.
     * Validates input and shows appropriate messages.
     * Updates book availability status.
     */
    private void returnBook() {
        String bookNo = txtBookNo.getText().trim();
        
        if (bookNo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter or select a book to return",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to return this book?",
            "Confirm Return",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.returnBook(bookNo)) {
                loadLendings();
                clearFields();
                bookPanel.refreshTable();
                JOptionPane.showMessageDialog(this,
                    "Book returned successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to return book. Please check if the book exists and is currently borrowed.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Clears all input fields and table selection.
     */
    private void clearFields() {
        txtBookNo.setText("");
        txtMemberNo.setText("");
        lendingTable.clearSelection();
    }
}