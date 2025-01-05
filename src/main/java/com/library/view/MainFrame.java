package com.library.view;

import javax.swing.*;

/**
 * Main application window
 * @author Your Name
 */
public class MainFrame extends JFrame {
    
    private JTabbedPane tabbedPane;
    private BookPanel bookPanel;
    private MemberPanel memberPanel;
    private LendingPanel lendingPanel;
    
    /**
     * Creates the main application window
     */
    public MainFrame() {
        initComponents();
    }
    
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