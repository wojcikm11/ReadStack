package com.example.Projekt_ReadStack.domain.discovery;

import com.example.Projekt_ReadStack.domain.common.BaseDao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiscoveryDao extends BaseDao {

    public List<Discovery> findAll() {
        final String query = """
        SELECT
          id, title, url, description, date_added, category_id, user_id
        FROM
          discovery d
        """;
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Discovery> allDiscoveries = new ArrayList<>();
            while (resultSet.next()) {
                Discovery discovery = mapRow(resultSet);
                allDiscoveries.add(discovery);
            }
            return allDiscoveries;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Discovery> findByCategory(int categoryId) {
        final String query = """
        SELECT
          id, title, url, description, date_added, category_id, user_id
        FROM
          discovery
        WHERE
          category_id = ?
        """;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            List<Discovery> discoveries = new ArrayList<>();
            while (resultSet.next()) {
                Discovery discovery = mapRow(resultSet);
                discoveries.add(discovery);
            }
            return discoveries;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Discovery discovery) {
        final String query = """
            INSERT INTO
              discovery(title, url, description, date_added, category_id, user_id)
            VALUES
              (?, ?, ?, ?, ?, ?)
        """;
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, discovery.getTitle());
            statement.setString(2, discovery.getUrl());
            statement.setString(3, discovery.getDescription());
            statement.setObject(4, discovery.getDateAdded());
            statement.setInt(5, discovery.getCategoryId());
            statement.setInt(6, discovery.getUserId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                discovery.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Discovery mapRow(ResultSet set) throws SQLException {
        int discoveryId = set.getInt("id");
        String title = set.getString("title");
        String url = set.getString("url");
        String description = set.getString("description");
        LocalDateTime dateAdded = set.getTimestamp("date_added").toLocalDateTime();
        int categoryId = set.getInt("category_id");
        int userId = set.getInt("user_id");
        return new Discovery(discoveryId, title, url, description, dateAdded, categoryId, userId);
    }
}
