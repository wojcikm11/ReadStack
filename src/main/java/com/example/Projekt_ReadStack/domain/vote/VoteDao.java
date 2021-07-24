package com.example.Projekt_ReadStack.domain.vote;

import com.example.Projekt_ReadStack.domain.common.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VoteDao extends BaseDao {

    public void save(Vote vote) {
        final String query = """
            INSERT INTO
              vote (user_id, discovery_id, type, date_added)
            VALUES
              (?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
              type = ?
        """;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vote.getUserId());
            statement.setInt(2, vote.getDiscoveryId());
            statement.setString(3, vote.getType().toString());
            statement.setObject(4, vote.getDateAdded());
            statement.setString(5, vote.getType().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(Vote vote) {
        final String query = """
            DELETE FROM
              vote
            WHERE
              user_id = ? AND discovery_id = ?
        """;
        try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, vote.getUserId());
        statement.setInt(2, vote.getDiscoveryId());
        statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vote> findVotesByUserId(int userId) {
        final String query = """
            SELECT
              user_id, discovery_id, type, date_added
            FROM
              vote
            WHERE
              user_id = ?
        """;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<Vote> userVotes = new ArrayList<>();
            while (resultSet.next()) {
                int discoveryId = resultSet.getInt("discovery_id");
                Vote.Type type = Vote.Type.valueOf(resultSet.getString("type"));
                LocalDateTime dateAdded = resultSet.getTimestamp("date_added").toLocalDateTime();
                userVotes.add(new Vote(userId, discoveryId, type, dateAdded));
            }
            return userVotes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Vote> findVote(int userId, int discoveryId) {
        final String query = """
            SELECT
              user_id, discovery_id, type, date_added
            FROM
              vote
            WHERE
              user_id = ? AND discovery_id = ?
        """;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, discoveryId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Vote.Type type = Vote.Type.valueOf(resultSet.getString("type"));
                LocalDateTime dateAdded = resultSet.getTimestamp("date_added").toLocalDateTime();
                Vote vote = new Vote(userId, discoveryId, type, dateAdded);
                return Optional.of(vote);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countByDiscoveryId(int discoveryId) {
        final String query = """
            SELECT
              (SELECT COUNT(discovery_id) FROM vote WHERE discovery_id = ? AND type = 'UP')
              -
              (SELECT COUNT(discovery_id) FROM vote WHERE discovery_id = ? AND type = 'DOWN')
            AS
              vote_count
        """;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, discoveryId);
            statement.setInt(2, discoveryId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("vote_count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
