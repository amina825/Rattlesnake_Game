package assignment_3;

/**
 * The Score class represents a score in the game.
 * It stores the player's name, score, and time.
 */
public class Score {
    private String name;
    private int score;
    private int time;

    /**
     * Constructs a new Score with the specified name, score, and time.
     *
     * @param name  the player's name
     * @param score the player's score
     * @param time  the time taken to achieve the score
     */
    public Score(String name, int score, int time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }

    /**
     * Gets the player's name.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's score.
     *
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the time taken to achieve the score.
     *
     * @return the time taken to achieve the score
     */
    public int getTime() {
        return time;
    }
}