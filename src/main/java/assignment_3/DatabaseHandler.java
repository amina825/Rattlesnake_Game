package assignment_3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The DatabaseHandler class manages the database connection and operations for scores.
 */
public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/highscores_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Aminaarooj1@";

    /**
     * Constructs a new DatabaseHandler and creates the scores table if it does not exist.
     */
    public DatabaseHandler() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (conn != null) {
                createTable();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates the scores table if it does not exist.
     */
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS scores ("
                   + "id INT AUTO_INCREMENT PRIMARY KEY, "
                   + "name VARCHAR(255) NOT NULL, "
                   + "score INT NOT NULL, "
                   + "time INT NOT NULL);"; 

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Inserts a new score into the database.
     *
     * @param name  the player's name
     * @param score the player's score
     * @param time  the time taken to achieve the score
     */
    public void insertScore(String name, int score, int time) {
        String sql = "INSERT INTO scores(name, score, time) VALUES(?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, score);
            pstmt.setInt(3, time);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieves the top 10 scores from the database, ordered by score and time.
     *
     * @return the list of scores
     */
    public ArrayList<Score> getScores() {
        String sql = "SELECT name, score, time FROM scores ORDER BY score DESC, time ASC LIMIT 10";
        ArrayList<Score> scores = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                scores.add(new Score(rs.getString("name"), rs.getInt("score"), rs.getInt("time")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return scores;
    }
}