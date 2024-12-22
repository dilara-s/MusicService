package ru.kpfu.itis.service;

import ru.kpfu.itis.dao.FavouriteDao;
import ru.kpfu.itis.dao.impl.FavouriteDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FavoriteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteService.class);
    private FavouriteDao favouriteDao;

    public FavoriteService(Connection connection) {
        this.favouriteDao = new FavouriteDaoImpl(connection);
    }

    public void toggleFavorite(Long userId, Long songId) throws SQLException {
        boolean isFavorite = favouriteDao.isFavorite(userId, songId);

        if (isFavorite) {
            favouriteDao.removeFavorite(userId, songId);
            LOGGER.info("Song with ID {} removed from user {}'s favorites", songId, userId);
        } else {
            favouriteDao.addFavorite(userId, songId);
            LOGGER.info("Song with ID {} added to user {}'s favorites", songId, userId);
        }
    }

    public List<Long> getUserFavoriteSongs(Long userId) throws SQLException {
        return favouriteDao.getFavoriteSongIds(userId);
    }

//    public boolean isSongInFavorites(Long userId, Long songId) throws SQLException {
//        return favoriteDao.isFavorite(userId, songId);
//    }
//
//    public void addSongToFavorites(Long userId, Long songId) throws SQLException {
//        if (favoriteDao.isFavorite(userId, songId)) {
//            LOGGER.info("Song with ID {} is already in user {}'s favorites", songId, userId);
//            return;
//        }
//
//        favoriteDao.addFavorite(userId, songId);
//        LOGGER.info("Song with ID {} added to user {}'s favorites", songId, userId);
//    }
//
//    public void removeSongFromFavorites(Long userId, Long songId) throws SQLException {
//        if (!favoriteDao.isFavorite(userId, songId)) {
//            LOGGER.info("Song with ID {} is not in user {}'s favorites", songId, userId);
//            return;
//        }
//
//        favoriteDao.removeFavorite(userId, songId);
//        LOGGER.info("Song with ID {} removed from user {}'s favorites", songId, userId);
//    }
}
