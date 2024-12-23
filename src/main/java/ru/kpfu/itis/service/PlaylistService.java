package ru.kpfu.itis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.PlaylistDao;
import ru.kpfu.itis.dao.FavouriteDao;
import ru.kpfu.itis.dao.impl.PlaylistDaoImpl;
import ru.kpfu.itis.dao.impl.FavouriteDaoImpl;
import ru.kpfu.itis.entity.Playlist;
import ru.kpfu.itis.entity.Song;

import java.sql.SQLException;
import java.util.List;

public class PlaylistService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaylistService.class);
    private final PlaylistDao playlistDao;
    private FavouriteDao favouriteDao;

    public PlaylistService(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    public void createPlaylist(Playlist playlist) throws SQLException {
        LOGGER.info("Creating playlist for userId: {}", playlist.getUserId());
        playlistDao.addPlaylist(playlist);
    }

    public void addSongToPlaylist(Long playlistId, Long songId) throws SQLException {
        LOGGER.info("Adding songId: {} to playlistId: {}", songId, playlistId);

        // Проверяем, что песня в избранном
        if (!favouriteDao.isFavorite(playlistId, songId)) {
            LOGGER.warn("SongId: {} is not in favorites, cannot add to playlistId: {}", songId, playlistId);
            throw new SQLException("Song is not in the user's favorites.");
        }

        playlistDao.addSongToPlaylist(playlistId, songId);
    }

    public void removeSongFromPlaylist(Long playlistId, Long songId) throws SQLException {
        LOGGER.info("Removing songId: {} from playlistId: {}", songId, playlistId);
        playlistDao.removeSongFromPlaylist(playlistId, songId);
    }

    public void removePlaylist(Long playlistId) throws SQLException {
        LOGGER.info("Removing playlist with id: {}", playlistId);
        playlistDao.removePlaylist(playlistId);
    }

    public Playlist getPlaylistById(Long playlistId) throws SQLException {
        LOGGER.info("Fetching playlist with id: {}", playlistId);
        return playlistDao.getPlaylistById(playlistId);
    }

    public List<Song> getSongsByPlaylistId(Long playlistId) throws SQLException {
        LOGGER.info("Fetching songs for playlistId: {}", playlistId);
        return playlistDao.getSongsByPlaylistId(playlistId);
    }

    public List<Playlist> getUserPlaylists(Long userId) throws SQLException {
        LOGGER.info("Fetching playlists for userId: {}", userId);
        return playlistDao.getUserPlaylists(userId);
    }
}
