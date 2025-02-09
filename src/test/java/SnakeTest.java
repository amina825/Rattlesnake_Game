package test.java;

import assignment_3.Score;
import assignment_3.Snake;
import assignment_3.Food;
import assignment_3.Rock;
import assignment_3.GamePanel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SnakeTest {

    // Test cases for HighScore class
    @Test
    public void testHighScoreGetName() {
        Score Score = new Score("Alice", 100, 120);
        assertEquals("Alice", Score.getName());
    }

    @Test
    public void testHighScoreGetScore() {
        Score highScore = new Score("Bob", 200, 150);
        assertEquals(200, highScore.getScore());
    }

    @Test
    public void testHighScoreGetTime() {
        Score highScore = new Score("Charlie", 300, 180);
        assertEquals(180, highScore.getTime());
    }

    // Test cases for Snake class
    @Test
    public void testSnakeInitialLength() {
        Snake snake = new Snake();
        assertEquals(2, snake.getLength());
    }

    @Test
    public void testSnakeGrow() {
        Snake snake = new Snake();
        snake.grow();
        assertEquals(3, snake.getLength());
    }

    @Test
    public void testSnakeMove() {
        Snake snake = new Snake();
        snake.move();
        Point head = snake.getSegments().getFirst();
        assertNotNull(head);
    }

    @Test
    public void testSnakeChangeDirection() {
        Snake snake = new Snake();
        snake.changeDirection(KeyEvent.VK_DOWN);
        snake.move();
        Point head = snake.getSegments().getFirst();
        assertNotNull(head);
    }

    @Test
    public void testSnakeCheckCollisionWithSelf() {
        Snake snake = new Snake();
        snake.grow();
        snake.grow();
        snake.grow();
        snake.changeDirection(KeyEvent.VK_DOWN);
        snake.move();
        snake.changeDirection(KeyEvent.VK_LEFT);
        snake.move();
        snake.changeDirection(KeyEvent.VK_UP);
        snake.move();
        snake.changeDirection(KeyEvent.VK_RIGHT);
        snake.move();
        assertTrue(snake.checkCollisionWithSelf());
    }


    @Test
    public void testSnakeCheckCollisionWithBoundary() {
        Snake snake = new Snake();
        snake.changeDirection(KeyEvent.VK_LEFT);
        for (int i = 0; i < 31; i++) { 
            snake.move();
        }
        assertTrue(snake.checkCollisionWithBoundary());
    }

    @Test
    public void testSnakeCheckCollisionWithFood() {
        Snake snake = new Snake();
        Food food = new Food();
        food.setPosition(300, 200);
        assertTrue(snake.checkCollisionWithFood(food));
    }

    @Test
    public void testSnakeCheckCollisionWithRocks() {
        Snake snake = new Snake();
        ArrayList<Rock> rocks = new ArrayList<>();
        rocks.add(new Rock(300, 200));
        assertTrue(snake.checkCollisionWithRocks(rocks));
    }

    // Test cases for GamePanel class
    @Test
    public void testGamePanelPauseGame() {
        GamePanel gamePanel = new GamePanel("easy");
        gamePanel.pauseGame();
        assertFalse(gamePanel.getTimer().isRunning());
    }

    @Test
    public void testGamePanelResumeGame() {
        GamePanel gamePanel = new GamePanel("easy");
        gamePanel.pauseGame();
        gamePanel.resumeGame();
        assertTrue(gamePanel.getTimer().isRunning());
    }

    @Test
    public void testGamePanelRestartGame() {
        GamePanel gamePanel = new GamePanel("easy");
        gamePanel.restartGame("medium");
        assertEquals("medium", gamePanel.getDifficulty());
    }

    // Test cases for Rock class
    @Test
    public void testRockGetX() {
        Rock rock = new Rock(100, 150);
        assertEquals(100, rock.getX());
    }

    @Test
    public void testRockGetY() {
        Rock rock = new Rock(100, 150);
        assertEquals(150, rock.getY());
    }

    @Test
    public void testRockDraw() {
        Rock rock = new Rock(100, 150);
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        rock.draw(g);
        assertNotNull(g);
    }
}