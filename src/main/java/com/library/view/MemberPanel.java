package com.library.view;

import com.library.controller.MemberController;
import com.library.model.Member;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for managing members
 * Provides interface for adding, updating, and viewing member information.
 * @author 22ENG 143,149,50
 */
public class MemberPanel extends JPanel {
    /** Controller for member operations */
    private MemberController controller;
    
     /** Table displaying member information */
    private JTable memberTable;
    
    /** Model for the member table */
    private DefaultTableModel tableModel;
    
    /** Text fields for member information */
    private JTextField txtMemberNo, txtFirstName, txtLastName, txtPhoneNo;
    
    /** Buttons for member operations */
    private JButton btnAdd, btnUpdate, btnClear;
    
     /**
     * Constructs a new MemberPanel.
     * Initializes the controller and UI components.
     */
    public MemberPanel() {
        controller = new MemberController();
        initComponents();
        loadMembers();
    }
    
    /**
     * Initializes and arranges all GUI components.
     * Sets up the input fields, buttons, and member table.
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Create input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Add input fields
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Member No:"), gbc);
        gbc.gridx = 1;
        txtMemberNo = new JTextField(20);
        inputPanel.add(txtMemberNo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        txtFirstName = new JTextField(20);
        inputPanel.add(txtFirstName, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        txtLastName = new JTextField(20);
        inputPanel.add(txtLastName, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Phone No:"), gbc);
        gbc.gridx = 1;
        txtPhoneNo = new JTextField(20);
        inputPanel.add(txtPhoneNo, gbc);
        
        // Add buttons
        JPanel buttonPanel = new JPanel();
        btnAdd = new JButton("Add Member");
        btnUpdate = new JButton("Update");
        btnClear = new JButton("Clear");
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnClear);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);
        
        // Create table
        String[] columns = {"Member No", "First Name", "Last Name", "Phone No"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        memberTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(memberTable);
        
        // Add components to panel
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add button listeners
        btnAdd.addActionListener(e -> addMember());
        btnUpdate.addActionListener(e -> updateMember());
        btnClear.addActionListener(e -> clearFields());
        
        // Add table selection listener
        memberTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = memberTable.getSelectedRow();
                if (row >= 0) {
                    txtMemberNo.setText((String) tableModel.getValueAt(row, 0));
                    txtFirstName.setText((String) tableModel.getValueAt(row, 1));
                    txtLastName.setText((String) tableModel.getValueAt(row, 2));
                    txtPhoneNo.setText((String) tableModel.getValueAt(row, 3));
                }
            }
        });
    }
    
    /**
     * Loads all members from the database and displays them in the table.
     */
    private void loadMembers() {
        tableModel.setRowCount(0);
        List<Member> members = controller.getAllMembers();
        for (Member member : members) {
            Object[] row = {
                member.getMemberNo(),
                member.getFirstName(),
                member.getLastName(),
                member.getPhoneNo()
            };
            tableModel.addRow(row);
        }
    }
    
    /**
     * Adds a new member using the input field values.
     * Validates input and shows appropriate messages.
     */
    private void addMember() {
        String memberNo = txtMemberNo.getText().trim();
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String phoneNo = txtPhoneNo.getText().trim();
        
        if (memberNo.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all required fields",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Member member = new Member(memberNo, firstName, lastName, phoneNo);
        if (controller.addMember(member)) {
            loadMembers();
            clearFields();
            JOptionPane.showMessageDialog(this,
                "Member added successfully",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Failed to add member",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Updates an existing member's information.
     * Validates input and shows appropriate messages.
     */
    private void updateMember() {
        int row = memberTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a member to update",
                "Selection Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String memberNo = txtMemberNo.getText().trim();
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String phoneNo = txtPhoneNo.getText().trim();
        
        if (memberNo.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all required fields",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Member member = controller.findByMemberNo(memberNo);
        if (member != null) {
            member.setFirstName(firstName);
            member.setLastName(lastName);
            member.setPhoneNo(phoneNo);
            
            if (controller.updateMember(member)) {
                loadMembers();
                clearFields();
                JOptionPane.showMessageDialog(this,
                    "Member updated successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to update member",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Clears all input fields and table selection.
     */
    private void clearFields() {
        txtMemberNo.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtPhoneNo.setText("");
        memberTable.clearSelection();
    }
}