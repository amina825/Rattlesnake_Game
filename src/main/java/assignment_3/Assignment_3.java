package assignment_3;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The Assignment_3 class represents the main frame of the Snake game.
 * It initializes the game panel and handles user interactions.
 */
public class Assignment_3 extends JFrame {
    private GamePanel gamePanel;

    /**
     * Constructs a new Assignment_3 frame and initializes the game.
     */
    public Assignment_3() {
        setTitle("Snake Game");
        String difficulty = selectDifficulty();
        gamePanel = new GamePanel(difficulty);
        add(gamePanel);
        setJMenuBar(gamePanel.createMenuBar());
        pack(); 
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null); 
        setVisible(true); 
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing();
            }
        });
    }

    /**
     * Handles the window closing event.
     * Prompts the user to confirm if they want to exit the game.
     */
    public void handleWindowClosing() {
        gamePanel.pauseGame(); 
        int confirm = JOptionPane.showConfirmDialog(this, "The game is not finished yet. Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0); 
        } else {
            gamePanel.resumeGame(); 
            System.out.println("Game resumed");
        }
    }

    /**
     * Prompts the user to select a difficulty level.
     *
     * @return the selected difficulty level
     */
    private String selectDifficulty() {
        String[] options = {"easy", "medium", "hard"};
        while (true) {
            String difficulty = (String) JOptionPane.showInputDialog(this, "Select Difficulty Level:", "Difficulty",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (difficulty == null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Game hasn't started yet. Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            } else {
                return difficulty;
            }
        }
    }

    /**
     * The main method to start the Snake game.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new Assignment_3().setVisible(true);
        });
    }
}