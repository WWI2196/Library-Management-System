package com.library.librarymanagementsystem;

import com.library.view.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main class for the Library Management System
 * @author Your Name
 */
public class LibraryManagementSystem {
    /**
     * Main method to launch the application
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Run the GUI in the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Set the System look and feel
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                );
            } catch (ClassNotFoundException | 
                     InstantiationException | 
                     IllegalAccessException | 
                     UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
            
            // Create and display the main window
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}