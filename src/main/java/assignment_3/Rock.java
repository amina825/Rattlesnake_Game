package assignment_3;

import java.awt.Graphics;
import java.awt.Color;

/**
 * The Rock class represents a rock in the game.
 * It manages the position and drawing of the rock.
 */
public class Rock {
    private int x, y;

    /**
     * Constructs a new Rock with default position.
     */
    public Rock() {
    }

    /**
     * Constructs a new Rock with the specified position.
     *
     * @param x the x-coordinate of the rock
     * @param y the y-coordinate of the rock
     */
    public Rock(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the rock.
     *
     * @return the x-coordinate of the rock
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the rock.
     *
     * @return the y-coordinate of the rock
     */
    public int getY() {
        return y;
    }

    /**
     * Draws the rock on the given graphics context.
     *
     * @param g the graphics context
     */
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, 10, 10);
    }
}