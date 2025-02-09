package assignment_3;

import java.util.Random;
import java.util.ArrayList;

/**
 * The Level class represents a level in the game.
 * It manages the rocks and food in the level.
 */
public class Level {
    private ArrayList<Rock> rocks;
    private Food food;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int PADDING = 20;

    public Level(String difficulty) {
        rocks = new ArrayList<>();
        food = new Food();
        generateLevel(difficulty);
    }

    /**
     * Gets the list of rocks in the level.
     *
     * @return the list of rocks
     */
    public ArrayList<Rock> getRocks() {
        return rocks;
    }

    /**
     * Sets the list of rocks in the level.
     *
     * @param rocks the list of rocks
     */
    public void setRocks(ArrayList<Rock> rocks) {
        this.rocks = rocks;
    }

    /**
     * Gets the food in the level.
     *
     * @return the food
     */
    public Food getFood() {
        return food;
    }

    /**
     * Sets the food in the level.
     *
     * @param food the food
     */
    public void setFood(Food food) {
        this.food = food;
    }

        /**
     * Generates the level by creating rocks and placing food based on the difficulty level.
     *
     * @param difficulty the difficulty level of the game
     */
    public void generateLevel(String difficulty) {
        int numRocks;
        switch (difficulty) {
            case "easy":
                numRocks = 5;
                break;
            case "medium":
                numRocks = 8;
                break;
            case "hard":
                numRocks = 10;
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty level: " + difficulty);
        }

        Random rand = new Random();
        for (int i = 0; i < numRocks; i++) {
            int x = PADDING + rand.nextInt((WIDTH - 2 * PADDING) / 10) * 10;
            int y = PADDING + rand.nextInt((HEIGHT - 2 * PADDING) / 10) * 10;
            rocks.add(new Rock(x, y));
        }

        int foodX = PADDING + rand.nextInt((WIDTH - 2 * PADDING) / 10) * 10;
        int foodY = PADDING + rand.nextInt((HEIGHT - 2 * PADDING) / 10) * 10;
        food.setPosition(foodX, foodY);
    }
}