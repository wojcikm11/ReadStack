package com.example.Projekt_ReadStack.domain.comment;

import com.example.Projekt_ReadStack.domain.common.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDao extends BaseDao {

    public List<Comment> findByDiscoveryId(int discoveryId) {
        final String query = """
            SELECT
              user_id, discovery_id, content, date_added
            FROM
              comment
            WHERE
              discovery_id = ?
        """;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, discoveryId);
            ResultSet resultSet = statement.executeQuery();
            List<Comment> discoveryComments = new ArrayList<>();
            while(resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String content = resultSet.getString("content");
                LocalDateTime dateAdded = resultSet.getTimestamp("date_added").toLocalDateTime();
                discoveryComments.add(new Comment(userId, discoveryId, content, dateAdded));
            }
            return discoveryComments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Comment comment) {
        final String query = """
            INSERT INTO
              comment(user_id, discovery_id, content, date_added)
            VALUES
              (?, ?, ?, ?)
        """;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, comment.getUserId());
            statement.setInt(2, comment.getDiscoveryId());
            statement.setString(3, comment.getContent());
            statement.setObject(4, comment.getDateAdded());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                comment.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countByDiscoveryId(int discoveryId) {
        final String query = """
            SELECT
              (SELECT COUNT(id) FROM comment WHERE discovery_id = ?)
            AS 
              comments_count
        """;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, discoveryId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("comments_count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
