package ru.kpfu.itis.dao.impl;

import ru.kpfu.itis.dao.FavouriteDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavouriteDaoImpl implements FavouriteDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FavouriteDaoImpl.class);
    private Connection connection;

    public FavouriteDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addFavorite(Long userId, Long songId) {
        String query = "INSERT INTO favourites (user_id, song_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, songId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error adding song to favorites", e);
        }
    }

    @Override
    public void removeFavorite(Long userId, Long songId) {
        String query = "DELETE FROM favourites WHERE user_id = ? AND song_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, songId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error removing song from favorites", e);
        }
    }

    @Override
    public List<Long> getFavoriteSongIds(Long userId) {
        List<Long> songIds = new ArrayList<>();
        String query = "SELECT song_id FROM favourites WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    songIds.add(rs.getLong("song_id"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching favorites for user", e);
        }
        return songIds;
    }

    @Override
    public boolean isFavorite(Long userId, Long songId) {
        String query = "SELECT COUNT(*) FROM favourites WHERE user_id = ? AND song_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, songId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking if song is in favorites", e);
        }
        return false;
    }

}
