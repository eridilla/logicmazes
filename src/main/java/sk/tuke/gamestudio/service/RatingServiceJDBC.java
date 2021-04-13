package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "eduard198";
    public static final String SELECTAVG = "SELECT AVG(rating) FROM ratings WHERE game = ?";
    public static final String SELECTRATING = "SELECT rating FROM ratings WHERE game = ? AND name = ?";
    public static final String DELETE = "DELETE FROM ratings";
    public static final String CHECKRATING = "SELECT * FROM ratings WHERE name = ?";
    public static final String INSERT = "INSERT INTO ratings (game, name, rating) VALUES (?, ?, ?)";
    public static final String UPDATERATING = "UPDATE ratings SET rating = ? WHERE name = ?";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(CHECKRATING)
        ) {
            statement.setString(1, rating.getName());

            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) {
                    try (PreparedStatement statement1 = connection.prepareStatement(INSERT)) {
                        statement1.setString(1, rating.getGame());
                        statement1.setString(2, rating.getName());
                        statement1.setInt(3, rating.getRating());
                        statement1.executeUpdate();
                    }
                } else {
                    try (PreparedStatement statement1 = connection.prepareStatement(UPDATERATING)) {
                        statement1.setInt(1, rating.getRating());
                        statement1.setString(2, rating.getName());
                        statement1.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem inserting rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECTAVG);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                int avgRating = 0;

                while (rs.next()) {
                    avgRating = rs.getInt(1);
                }

                return avgRating;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting average rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECTRATING);
        ) {
            statement.setString(1, game);
            statement.setString(2, player);
            try (ResultSet rs = statement.executeQuery()) {
                int playerRating = 0;

                while (rs.next()) {
                    playerRating = rs.getInt(1);
                }

                return playerRating;
            }
        } catch (SQLException e) {
            throw new ScoreException("Problem selecting rating", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting rating", e);
        }
    }
}
