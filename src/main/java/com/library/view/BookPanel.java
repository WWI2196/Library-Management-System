package com.library.view;

import com.library.controller.BookController;
import com.library.model.Book;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * A GUI panel for managing books in the library system.
 * This panel provides functionality for adding, updating, and managing book availability.
 * It displays books in a table format and provides input fields for book details.
 * 
 * @author 22ENG 143,149,50
 */
public class BookPanel extends JPanel {
    /** Controller for handling book-related operations */
    private BookController controller;
    
    /** Table to display the list of books */
    private JTable bookTable;
    
    /** Model for the book table data */
    private DefaultTableModel tableModel;
    
    /** Text field for entering/displaying book number */
    private JTextField txtBookNo;
    
    /** Text field for entering/displaying book title */
    private JTextField txtTitle;
    
    /** Text field for entering/displaying book author */
    private JTextField txtAuthor;
    
    /** Button for adding new books */
    private JButton btnAdd;
    
    /** Button for updating existing books */
    private JButton btnUpdate;
    
    /** Button for clearing input fields */
    private JButton btnClear;
    
    /** Button for making a book available */
    private JButton btnMakeAvailable;
    
    /** Button for making a book unavailable */
    private JButton btnMakeUnavailable;
    
    /**
     * Constructs a new BookPanel.
     * Initializes the controller and components, and loads existing books.
     */
    public BookPanel() {
        controller = new BookController();
        initComponents();
        loadBooks();
    }
    
    /**
     * Initializes and arranges all GUI components of the panel.
     * Sets up the input fields, buttons, table, and their respective event listeners.
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
    
    /**
     * Loads all books from the database and displays them in the table.
     * Clears the existing table data before loading new data.
     */
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
    
    /**
     * Handles the addition of a new book to the system.
     * Validates input fields and shows appropriate success or error messages.
     */
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
    
     /**
     * Updates the details of an existing book in the system.
     * Requires a book to be selected in the table.
     * Validates input fields and shows appropriate success or error messages.
     */
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
    
    /**
     * Makes a selected book available for lending.
     * Checks if the book is currently lent out before making it available.
     * Shows appropriate success or error messages.
     */
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
    
    /**
     * Makes a selected book unavailable for lending.
     * Shows appropriate success or error messages.
     */
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
    
    /**
     * Refreshes the book table to reflect current data in the system.
     * This method can be called from outside the class to update the display.
     */
    public void refreshTable() {
        loadBooks();
    }
    
    /**
     * Clears all input fields and deselects any selected row in the table.
     */
    private void clearFields() {
        txtBookNo.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        bookTable.clearSelection();
    }
}