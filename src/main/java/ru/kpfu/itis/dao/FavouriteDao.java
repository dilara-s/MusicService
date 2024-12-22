package ru.kpfu.itis.dao;

import java.sql.SQLException;
import java.util.List;

public interface FavouriteDao {

    public void addFavorite(Long userId, Long songId) throws SQLException;

    public void removeFavorite(Long userId, Long songId) throws SQLException;

    public List<Long> getFavoriteSongIds(Long userId) throws SQLException;

    public boolean isFavorite(Long userId, Long songId) throws SQLException;
}
