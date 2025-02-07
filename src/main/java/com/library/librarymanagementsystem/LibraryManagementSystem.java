package com.library.librarymanagementsystem;

import com.library.view.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main class for the Library Management System application.
 * This class initializes the GUI and sets up the application environment.
 * 
 * @author 22ENG 143,149,50
 * @version 1.0
 */
public class LibraryManagementSystem {
    /**
     * Main method that bootstraps the application.
     * Initializes the GUI using Swing and sets the system look and feel.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                /**Set the System look and feel*/
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            }
            
            /**Create and display the main window*/
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
