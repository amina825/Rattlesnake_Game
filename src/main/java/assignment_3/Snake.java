package assignment_3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;
import java.util.ArrayList;

/**
 * The Snake class represents the snake in the game.
 * It manages the snake's segments, direction, and movement.
 */
public class Snake {
    private LinkedList<Point> segments;
    private int direction;
    private static final int INITIAL_LENGTH = 2;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    /**
     * Constructs a new Snake with an initial length and random direction.
     */
    public Snake() {
        segments = new LinkedList<>();
        segments.add(new Point(300, 200)); // Initial position
        segments.add(new Point(290, 200)); // Initial position

        // Initialize direction randomly
        Random rand = new Random();
        int randomDirection = rand.nextInt(4);
        switch (randomDirection) {
            case 0 -> direction = KeyEvent.VK_UP;
            case 1 -> direction = KeyEvent.VK_DOWN;
            case 2 -> direction = KeyEvent.VK_LEFT;
            case 3 -> direction = KeyEvent.VK_RIGHT;
        }
    }

    /**
     * Changes the direction of the snake.
     *
     * @param keyCode the key code representing the new direction
     */
    public void changeDirection(int keyCode) {
        if ((keyCode == KeyEvent.VK_UP && direction != KeyEvent.VK_DOWN) ||
            (keyCode == KeyEvent.VK_W && direction != KeyEvent.VK_DOWN) ||
            (keyCode == KeyEvent.VK_DOWN && direction != KeyEvent.VK_UP) ||
            (keyCode == KeyEvent.VK_S && direction != KeyEvent.VK_UP) ||
            (keyCode == KeyEvent.VK_LEFT && direction != KeyEvent.VK_RIGHT) ||
            (keyCode == KeyEvent.VK_A && direction != KeyEvent.VK_RIGHT) ||
            (keyCode == KeyEvent.VK_RIGHT && direction != KeyEvent.VK_LEFT) ||
            (keyCode == KeyEvent.VK_D && direction != KeyEvent.VK_LEFT)) {
            direction = keyCode;
        }
    }

    /**
     * Moves the snake in the current direction.
     */
    public void move() {
        Point head = segments.getFirst();
        int x = head.x;
        int y = head.y;
        switch (direction) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> y -= 10;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> y += 10;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> x -= 10;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> x += 10;
        }
        //snake head
        segments.addFirst(new Point(x, y));
        segments.removeLast();
    }

    /**
     * Grows the snake by adding a new segment at the tail.
     */
    public void grow() {
        Point tail = segments.getLast();
        segments.addLast(new Point(tail.x, tail.y));
    }

    /**
     * Checks if the snake collides with any rocks.
     *
     * @param rocks the list of rocks
     * @return true if the snake collides with any rock, false otherwise
     */
    public boolean checkCollisionWithRocks(ArrayList<Rock> rocks) {
        Point head = segments.getFirst();
        for (Rock rock : rocks) {
            if (head.x == rock.getX() && head.y == rock.getY()) {
                System.out.println("Collision with rock at: " + rock.getX() + ", " + rock.getY()); // Debug statement
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the snake collides with itself.
     *
     * @return true if the snake collides with itself, false otherwise
     */
    public boolean checkCollisionWithSelf() {
        Point head = segments.getFirst();
        for (int i = 1; i < segments.size(); i++) {
            if (head.equals(segments.get(i))) {
                System.out.println("Collision with self at: " + segments.get(i).x + ", " + segments.get(i).y); // Debug statement
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the snake collides with the boundary.
     *
     * @return true if the snake collides with the boundary, false otherwise
     */
    public boolean checkCollisionWithBoundary() {
        Point head = segments.getFirst();
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            System.out.println("Collision with boundary at: " + head.x + ", " + head.y); // Debug statement
            return true;
        }
        return false;
    }

    /**
     * Checks if the snake collides with food.
     *
     * @param food the food object
     * @return true if the snake collides with the food, false otherwise
     */
    public boolean checkCollisionWithFood(Food food) {
        Point head = segments.getFirst();
        return head.x == food.getX() && head.y == food.getY();
    }

    /**
     * Gets the length of the snake.
     *
     * @return the length of the snake
     */
    public int getLength() {
        return segments.size();
    }

    /**
     * Gets the number of food items eaten by the snake.
     *
     * @return the number of food items eaten by the snake
     */
    public int getFoodEaten() {
        return segments.size() - INITIAL_LENGTH;
    }

    /**
     * Gets the segments of the snake.
     *
     * @return the segments of the snake
     */
    public LinkedList<Point> getSegments() {
        return segments;
    }

    /**
     * Draws the snake on the given graphics context.
     *
     * @param g the graphics context
     */
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point segment : segments) {
            g.fillRect(segment.x, segment.y, 10, 10);
        }
    }
}