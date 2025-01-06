package com.library.view;

import com.library.controller.BookController;
import com.library.model.Book;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for managing books
 * @author 22ENG 143,149,50
 */
public class BookPanel extends JPanel {
    private BookController controller;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField txtBookNo, txtTitle, txtAuthor;
    private JButton btnAdd, btnUpdate, btnClear;
    private JButton btnMakeAvailable,btnMakeUnavailable;
    
    public BookPanel() {
        controller = new BookController();
        initComponents();
        loadBooks();
    }
    
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
        inputPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        txtTitle = new JTextField(20);
        inputPanel.add(txtTitle, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        txtAuthor = new JTextField(20);
        inputPanel.add(txtAuthor, gbc);
        
        // Add buttons
        JPanel buttonPanel = new JPanel();
        btnAdd = new JButton("Add Book");
        btnUpdate = new JButton("Update");
        btnClear = new JButton("Clear");
        btnMakeAvailable = new JButton("Make Available");
        btnMakeUnavailable = new JButton("Make Unavailable");
        
        // Add action listener for Make Available button
        btnMakeAvailable.addActionListener(e -> makeBookAvailable());
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnMakeAvailable);
        buttonPanel.add(btnMakeUnavailable);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);
        
        // Create table
        String[] columns = {"Book No", "Title", "Author", "Available"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        
        // Add components to panel
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add button listeners
        btnAdd.addActionListener(e -> addBook());
        btnUpdate.addActionListener(e -> updateBook());
        btnClear.addActionListener(e -> clearFields()); 
        btnMakeAvailable.addActionListener(e -> makeBookAvailable());
        btnMakeUnavailable.addActionListener(e -> makeBookUnavailable());
        
        // Add table selection listener
        bookTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = bookTable.getSelectedRow();
                if (row >= 0) {
                    txtBookNo.setText((String) tableModel.getValueAt(row, 0));
                    txtTitle.setText((String) tableModel.getValueAt(row, 1));
                    txtAuthor.setText((String) tableModel.getValueAt(row, 2));
                }
            }
        });
    }
    
    private void loadBooks() {
        tableModel.setRowCount(0);
        List<Book> books = controller.getAllBooks();
        for (Book book : books) {
            Object[] row = {
                book.getBookNo(),
                book.getTitle(),
                book.getAuthor(),
                book.isAvailable() ? "Yes" : "No"
            };
            tableModel.addRow(row);
        }
    }
    
    private void addBook() {
        String bookNo = txtBookNo.getText().trim();
        String title = txtTitle.getText().trim();
        String author = txtAuthor.getText().trim();
        
        if (bookNo.isEmpty() || title.isEmpty() || author.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Book book = new Book(bookNo, title, author);
        if (controller.addBook(book)) {
            loadBooks();
            clearFields();
            JOptionPane.showMessageDialog(this, 
                "Book added successfully", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to add book", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateBook() {
        int row = bookTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, 
                "Please select a book to update", 
                "Selection Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String bookNo = txtBookNo.getText().trim();
        String title = txtTitle.getText().trim();
        String author = txtAuthor.getText().trim();
        
        if (bookNo.isEmpty() || title.isEmpty() || author.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Book book = controller.findByBookNo(bookNo);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            
            if (controller.updateBook(book)) {
                loadBooks();
                clearFields();
                JOptionPane.showMessageDialog(this, 
                    "Book updated successfully", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to update book", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void makeBookAvailable() {
        int row = bookTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a book to make available",
                "Selection Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String bookNo = (String) tableModel.getValueAt(row, 0);
        
        // Check if book is currently lent out
        if (controller.isBookLent(bookNo)) {
            JOptionPane.showMessageDialog(this,
                "Cannot make book available: Book is currently lent out.\n" +
                "Please use the Lending panel to return the book first.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (controller.setBookAvailability(bookNo, true)) {
            loadBooks(); // Refresh the table
            clearFields();
            JOptionPane.showMessageDialog(this,
                "Book is now available",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Failed to update book availability",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Add new method for making book unavailable
    private void makeBookUnavailable() {
        int row = bookTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a book to make unavailable",
                "Selection Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String bookNo = (String) tableModel.getValueAt(row, 0);
        if (controller.setBookAvailability(bookNo, false)) {
            loadBooks(); // Refresh the table
            clearFields();
            JOptionPane.showMessageDialog(this,
                "Book is now unavailable",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Failed to update book availability",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void refreshTable() {
        loadBooks();
    }
    
    private void clearFields() {
        txtBookNo.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        bookTable.clearSelection();
    }
}