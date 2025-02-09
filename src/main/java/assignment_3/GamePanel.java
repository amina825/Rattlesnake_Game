package assignment_3;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;


/**
 * The GamePanel class represents the game panel in the Snake game.
 * It manages the game logic, rendering, and user interactions.
 */
public class GamePanel extends JPanel {
    private Snake snake;
    private Level level;
    private Timer timer;
    private DatabaseHandler dbHandler;
    private long startTime;
    private long elapsedTime;
    private long pauseTime;
    private String difficulty;

    /**
     * Constructs a new GamePanel with the specified difficulty.
     *
     * @param difficulty the difficulty level of the game
     */
    public GamePanel(String difficulty) {
        this.difficulty = difficulty;
        snake = new Snake();
        level = new Level(difficulty);
        dbHandler = new DatabaseHandler();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                snake.changeDirection(e.getKeyCode());
            }
        });
        setFocusable(true);
        setPreferredSize(new java.awt.Dimension(600, 400)); 
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
        timer = new Timer(100, e -> {
            snake.move();
            checkCollisions();
            elapsedTime = (System.currentTimeMillis() - startTime) / 1000; 
            repaint();
        });
        timer.start();
    }

    /**
     * Pauses the game.
     */
    public void pauseGame() {
        timer.stop();
        pauseTime = System.currentTimeMillis();
    }

    /**
     * Resumes the game.
     */
    public void resumeGame() {
        timer.start();
        startTime += (System.currentTimeMillis() - pauseTime); 
    }

    /**
     * Gets the difficulty level of the game.
     *
     * @return the difficulty level
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Checks for collisions with rocks, self, and boundaries.
     */
    public void checkCollisions() {
        if (snake.checkCollisionWithRocks(level.getRocks()) || snake.checkCollisionWithSelf() || snake.checkCollisionWithBoundary()) {
            timer.stop();
            while (true) {
                String playerName = JOptionPane.showInputDialog(this, "Game Over! Enter your name:");
                if (playerName == null || playerName.trim().isEmpty()) {
                    int confirm = JOptionPane.showConfirmDialog(this, "You haven't entered the name yet. Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        int playAgain = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
                        if (playAgain == JOptionPane.YES_OPTION) {
                            String newDifficulty = selectDifficulty();
                            if (newDifficulty != null) {
                                restartGame(newDifficulty); 
                            }
                        } else {
                            System.exit(0); 
                        }
                        break;
                    }
                } else {
                    dbHandler.insertScore(playerName, snake.getFoodEaten(), (int) elapsedTime);
                    showHighscoreTable();
                    int playAgain = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
                    if (playAgain == JOptionPane.YES_OPTION) {
                        String newDifficulty = selectDifficulty();
                        if (newDifficulty != null) {
                            restartGame(newDifficulty); 
                        }
                    } else {
                        System.exit(0); 
                    }
                    break;
                }
            }
            return; 
        }
        if (snake.checkCollisionWithFood(level.getFood())) {
            snake.grow();
            level.getFood().relocate();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.draw(g);
        level.getFood().draw(g);
        for (Rock rock : level.getRocks()) {
            rock.draw(g);
        }
        long minutes = elapsedTime / 60;
        long seconds = elapsedTime % 60;
        String timeString = String.format("Time: %02d:%02d", minutes, seconds);
        g.drawString(timeString, 10, 10); 
    }

    /**
     * Creates the menu bar for the game.
     *
     * @return the menu bar
     */
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        gameMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                pauseGame(); // Pause the game 
            }

            @Override
            public void menuDeselected(MenuEvent e) {
                resumeGame(); // Resume the game
            }

            @Override
            public void menuCanceled(MenuEvent e) {
                resumeGame(); // Resume the game
            }
        });

        JMenuItem restartItem = new JMenuItem("Restart");
        restartItem.addActionListener(e -> restartGame(difficulty));
        gameMenu.add(restartItem);

        JMenuItem highscoreItem = new JMenuItem("Highscore");
        highscoreItem.addActionListener(e -> showHighscore());
        gameMenu.add(highscoreItem);

        menuBar.add(gameMenu);
        return menuBar;
    }

    /**
     * Gets the timer for the game.
     *
     * @return the timer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Restarts the game with the specified difficulty.
     *
     * @param difficulty the difficulty level
     */
    public void restartGame(String difficulty) {
        this.difficulty = difficulty; 
        snake = new Snake();
        level = new Level(difficulty); 
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
        timer.start();
    }

    /**
     * Prompts the user to select a difficulty level.
     *
     * @return the selected difficulty level
     */
    public String selectDifficulty() {
        String[] options = {"easy", "medium", "hard"};
        while (true) {
            difficulty = (String) JOptionPane.showInputDialog(this, "Select Difficulty Level:", "Difficulty",
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
     * Displays the high scores.
     */
    public void showHighscore() {
        pauseGame(); // Pause the game before displaying highscores
        ArrayList<Score> highScores = dbHandler.getScores();
        String[] columnNames = {"Name", "Score", "Time"};
        Object[][] data = new Object[highScores.size()][3];
        for (int i = 0; i < highScores.size(); i++) {
           Score hs = highScores.get(i);
           data[i][0] = hs.getName();
           data[i][1] = hs.getScore();
           long minutes = hs.getTime() / 60;
           long seconds = hs.getTime() % 60;
           String timeString = String.format("%02d:%02d", minutes, seconds);
           data[i][2] = timeString;
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JDialog dialog = new JDialog();
        dialog.setTitle("High Scores");
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        dialog.add(panel);
        dialog.setVisible(true);
        resumeGame(); 
    }


    /**
     * Displays the high scores table in a dialog.
     */
    public void showHighscoreTable() {
        ArrayList<Score> highScores = dbHandler.getScores();
        String[] columnNames = {"Name", "Score", "Time"};
        Object[][] data = new Object[highScores.size()][3];
        for (int i = 0; i < highScores.size(); i++) {
            Score hs = highScores.get(i);
            data[i][0] = hs.getName();
            data[i][1] = hs.getScore();
            long minutes = hs.getTime() / 60;
            long seconds = hs.getTime() % 60;
            String timeString = String.format("%02d:%02d", minutes, seconds);
            data[i][2] = timeString;
        }
    
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
    
        JDialog dialog = new JDialog();
        dialog.setTitle("High Scores");
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
    
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);
    
        dialog.add(panel);
        dialog.setVisible(true);
    }

}