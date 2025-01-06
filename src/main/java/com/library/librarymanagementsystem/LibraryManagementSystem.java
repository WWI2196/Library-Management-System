package com.library.librarymanagementsystem;

import com.library.view.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main class for the Library Management System application.
 * 
 * @author 22ENG 143,149,50
 * @version 1.0
 */
public class LibraryManagementSystem {
    /**
     * Main method that launches the application.
     * Sets up the main application window.
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

/**
 * Group Members
 *  22/ENG/149 W.W.Ilukkumbure
 *  22/ENG/50 H.M.R.V.Herath
 *  22/ENG/143 S.S.Konthasinghe
 */