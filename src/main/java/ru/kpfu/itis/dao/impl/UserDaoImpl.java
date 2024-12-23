package ru.kpfu.itis.dao.impl;

import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.util.DatabaseCollectionUtil;

import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = DatabaseCollectionUtil.getConnection();
    }

    @Override
    public void addUser(User user) throws SQLException {
        LOGGER.info("Adding new user: {}", user.getUsername());

        String insertQuery = "INSERT INTO users(username, email, password, avatar_image) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getAvatarImage());

            int rowsAffected = statement.executeUpdate();
            LOGGER.info("User added successfully. Rows affected: {}", rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error while adding user: {}", user.getUsername(), e);
            throw e;
        }
    }

    @Override
    public User getUsernameAndPassword(String username, String password) throws SQLException {
        LOGGER.info("Fetching user with username: {}", username);

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error while fetching user with username: {}", username, e);
            throw e;
        }
        return null;
    }


    @Override
    public User getUserById(Long id) throws SQLException {
        LOGGER.info("Fetching user with ID: {}", id);

        String query = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error while fetching user with ID: {}", id, e);
            throw e;
        }
        return null;
    }

    @Override
    public Long getUserId(String username) throws SQLException {
        LOGGER.info("Fetching user ID for email: {}", username);

        String query = "SELECT id FROM users WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error while fetching user ID for email: {}", username, e);
            throw e;
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        LOGGER.info("Fetching user with email: {}", email);

        String query = "SELECT * FROM users WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error while fetching user with email: {}", email, e);
            throw e;
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        LOGGER.info("Updating user with ID: {}", user.getId());

        String updateQuery = "UPDATE users SET username = ?, email = ?, password = ?, avatar_image = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getAvatarImage());
            statement.setLong(5, user.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("User updated successfully. Rows affected: {}", rowsAffected);
                return true;
            } else {
                LOGGER.warn("No user was updated with ID: {}", user.getId());
                return false;
            }
        } catch (SQLException e) {
            LOGGER.error("Error while updating user with ID: {}", user.getId(), e);
            throw e;
        }
    }


    @Override
    public boolean updatePassword(User user) throws SQLException {
        LOGGER.info("Updating password for user with ID: {}", user.getId());

        String updateQuery = "UPDATE users SET password = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, user.getPassword());
            statement.setLong(2, user.getId());

            int rowsAffected = statement.executeUpdate();
            LOGGER.info("Password updated successfully. Rows affected: {}", rowsAffected);

            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.error("Error while updating password for user with ID: {}", user.getId(), e);
            throw e;
        }
    }

    @Override
    public void deleteUser(Long id) throws SQLException {
        LOGGER.info("Deleting user with ID: {}", id);

        String deleteQuery = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();
            LOGGER.info("User deleted successfully. Rows affected: {}", rowsAffected);
        } catch (SQLException e) {
            LOGGER.error("Error while deleting user with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public boolean isUserExists(String email) throws SQLException {
        LOGGER.info("Checking if user exists with email: {}", email);

        String query = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error while checking if user exists with email: {}", email, e);
            throw e;
        }
        return false;
    }

    // Вспомогательный метод для преобразования строки ResultSet в объект User
    private User mapRowToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setAvatarImage(resultSet.getString("avatar_image"));
        return user;
    }
}
