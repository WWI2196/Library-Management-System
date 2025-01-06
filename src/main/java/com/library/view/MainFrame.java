package com.library.view;

import javax.swing.*;

/**
 * Main application window
 * Provides a tabbed interface for accessing Books, Members, and Lending functionality.
 * @author 22ENG 143,149,50
 */
public class MainFrame extends JFrame {
    
    /** Tabbed pane containing all main panels */
    private JTabbedPane tabbedPane;
    
    /** Panel for managing books */
    private BookPanel bookPanel;
    
    /** Panel for managing members */
    private MemberPanel memberPanel;
    
    /** Panel for managing lending operations */
    private LendingPanel lendingPanel;
    
    /**
     * Constructs the main application window and initializes all components.
     */
    public MainFrame() {
        initComponents();
    }
    
    /**
     * Initializes and arranges all GUI components.
     * Sets up the tabbed interface and adds all management panels.
     */
    private void initComponents() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Create panels
        bookPanel = new BookPanel();
        memberPanel = new MemberPanel();
        lendingPanel = new LendingPanel(bookPanel);
        
        // Add panels to tabbed pane
        tabbedPane.addTab("Books", bookPanel);
        tabbedPane.addTab("Members", memberPanel);
        tabbedPane.addTab("Lending", lendingPanel);
        
        // Add tabbed pane to frame
        add(tabbedPane);
        
        // Set frame properties
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen
    }
}