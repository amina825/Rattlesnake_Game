package assignment_3;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

/**
 * The Food class represents the food in the game.
 * It manages the position and drawing of the food.
 */
public class Food {
    private int x, y;
    private static final int WIDTH = 600; 
    private static final int HEIGHT = 400; 
    private static final int PADDING = 20;

    /**
     * Constructs a new Food object and relocates it to a random position.
     */
    public Food() {
        relocate();
    }

    /**
     * Relocates the food to a random position within the game area.
     */
    public void relocate() {
        Random rand = new Random();
        x = PADDING + rand.nextInt((WIDTH - 2 * PADDING) / 10) * 10;
        y = PADDING + rand.nextInt((HEIGHT - 2 * PADDING) / 10) * 10;
    }

    /**
     * Sets the position of the food.
     *
     * @param x the x-coordinate of the food
     * @param y the y-coordinate of the food
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the food.
     *
     * @return the x-coordinate of the food
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the food.
     *
     * @return the y-coordinate of the food
     */
    public int getY() {
        return y;
    }

    /**
     * Draws the food on the given graphics context.
     *
     * @param g the graphics context
     */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 10, 10);
    }
}