package com.example.Projekt_ReadStack.domain.user;

import com.example.Projekt_ReadStack.domain.common.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDetailsDao extends BaseDao {

    public void update(UserDetails userDetails) {
        final String query = """
            UPDATE
              user_details
            SET
              first_name = ?,
              last_name = ?,
              gender = ?,
              age = ?,
              description = ?
            WHERE
              user_id = ?
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userDetails.getFirstName());
            statement.setString(2, userDetails.getLastName());
            if (userDetails.getGender() == null) {
                statement.setObject(3, null);
            } else {
                statement.setString(3, userDetails.getGender().name());
            }
            statement.setInt(4, userDetails.getAge());
            statement.setString(5, userDetails.getDescription());
            statement.setInt(6, userDetails.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<UserDetails> findByUserId(int userId) {
        final String query = """
            SELECT
              id, user_id, first_name, last_name, gender, age, description
            FROM
              user_details
            WHERE
              user_id = ?
        """;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapRow(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void create(UserDetails userDetails) {
        final String query = """
            INSERT INTO
              user_details 
              (user_id, first_name, last_name, gender, age, description)
            VALUES
              (?, ?, ?, ?, ?, ?)
        """;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userDetails.getUserId());
            statement.setString(2, userDetails.getFirstName());
            statement.setString(3, userDetails.getLastName());
            if (userDetails.getGender() == null) {
                statement.setObject(4,null);
            } else {
                statement.setString(4, userDetails.getGender().name());
            }
            statement.setInt(5, userDetails.getAge());
            statement.setString(6, userDetails.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private UserDetails mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int userId = resultSet.getInt("user_id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        Gender gender = null;
        if (resultSet.getObject("gender") != null) {
            gender =  Gender.valueOf(resultSet.getString("gender"));
        }
        int age = resultSet.getInt("age");
        String description = resultSet.getString("description");
        return new UserDetails(id, userId, firstName, lastName, gender, age, description);
    }
}
