package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "eduard198";
    public static final String SELECT = "SELECT name, comment, commentedon, game, commentid FROM comments WHERE game = ? ORDER BY commentid DESC LIMIT 10";
    public static final String INSERT = "INSERT INTO comments (name, comment, commentedon, game) VALUES (?, ?, ?, ?)";
    public static final String DELETE = "DELETE FROM comments";

    @Override
    public void addComment(Comment comment) throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, comment.getName());
            statement.setString(2, comment.getComment());
            statement.setTimestamp(3, new Timestamp(comment.getDate().getTime()));
            statement.setString(4, comment.getGame());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CommentException("Problem inserting comment", e);
        }
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    return null;
                }

                List<Comment> comments = new ArrayList<>();

                while (rs.next()) {
                    comments.add(new Comment(rs.getString(1), rs.getString(2), rs.getTimestamp(3), rs.getString(4)));
                }

                return comments;
            }
        } catch (SQLException e) {
            throw new CommentException("Problem selecting comment", e);
        }
    }

    @Override
    public void reset() throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new CommentException("Problem deleting comment.", e);
        }
    }
}
