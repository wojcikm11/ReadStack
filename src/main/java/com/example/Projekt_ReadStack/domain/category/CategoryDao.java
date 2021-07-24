package com.example.Projekt_ReadStack.domain.category;

import com.example.Projekt_ReadStack.domain.common.BaseDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDao extends BaseDao {

    public List<Category> findAll() {
        final String query = """
            SELECT
              id, name, description
            FROM
              category c
        """;
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Category> allCategories = new ArrayList<>();
            while (resultSet.next()) {
                Category category = mapRow(resultSet);
                allCategories.add(category);
            }
            return allCategories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Category> findById(int id) {
        final String query = """
            SELECT 
              id, name, description
            FROM
              category
            WHERE
              id = ?
    """;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Category category = mapRow(resultSet);
                return Optional.of(category);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Category mapRow(ResultSet set) throws SQLException {
        int categoryId = set.getInt("id");
        String name = set.getString("name");
        String description = set.getString("description");
        return new Category(categoryId, name, description);
    }
}
