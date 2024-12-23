package ru.kpfu.itis.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.PlaylistDao;
import ru.kpfu.itis.dao.FavouriteDao;
import ru.kpfu.itis.entity.Playlist;
import ru.kpfu.itis.entity.Song;
import ru.kpfu.itis.util.DatabaseCollectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDaoImpl implements PlaylistDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaylistDaoImpl.class);
    private Connection connection;
    private FavouriteDao favouriteDao;

    public PlaylistDaoImpl(Connection connection) {
        this.connection = DatabaseCollectionUtil.getConnection();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void addPlaylist(Playlist playlist) throws SQLException {
        String query = "INSERT INTO playlists (user_id, title) VALUES (?, ?)";
        LOGGER.info("Attempting to add a playlist for userId: {}", playlist.getUserId());

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, playlist.getUserId());
            statement.setString(2, playlist.getTitle());
            statement.executeUpdate();
            LOGGER.info("Playlist added successfully with title: {}", playlist.getTitle());
        } catch (SQLException e) {
            LOGGER.error("Error while adding playlist for userId: {}", playlist.getUserId(), e);
            throw new SQLException("Error while adding playlist", e);
        }
    }

    @Override
    public void addSongToPlaylist(Long playlistId, Long songId) throws SQLException {
        LOGGER.info("Attempting to add songId: {} to playlistId: {}", songId, playlistId);

        if (!favouriteDao.isFavorite(playlistId, songId)) {
            LOGGER.warn("SongId: {} is not in favorites, cannot add to playlistId: {}", songId, playlistId);
            throw new SQLException("Song is not in user's favorites and cannot be added to the playlist.");
        }

        String query = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, playlistId);
            statement.setLong(2, songId);
            statement.executeUpdate();
            LOGGER.info("SongId: {} added to playlistId: {}", songId, playlistId);
        } catch (SQLException e) {
            LOGGER.error("Error while adding songId: {} to playlistId: {}", songId, playlistId, e);
            throw new SQLException("Error while adding song to playlist", e);
        }
    }

    @Override
    public Playlist getPlaylistById(Long id) throws SQLException {
        String query = "SELECT * FROM playlists WHERE id = ?";
        Playlist playlist = null;
        LOGGER.info("Fetching playlist with id: {}", id);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    playlist = mapRowToPlaylist(resultSet);
                    LOGGER.info("Playlist found with id: {}", id);
                } else {
                    LOGGER.warn("Playlist not found with id: {}", id);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error while fetching playlist with id: {}", id, e);
            throw new SQLException("Error while fetching playlist", e);
        }
        return playlist;
    }

    @Override
    public List<Song> getSongsByPlaylistId(Long playlistId) throws SQLException {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT s.* FROM songs s " +
                "JOIN playlist_songs ps ON s.id = ps.song_id " +
                "WHERE ps.playlist_id = ?";
        LOGGER.info("Fetching songs for playlistId: {}", playlistId);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, playlistId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    songs.add(mapRowToSong(resultSet));
                }
                LOGGER.info("Fetched {} songs for playlistId: {}", songs.size(), playlistId);
            }
        } catch (SQLException e) {
            LOGGER.error("Error while fetching songs for playlistId: {}", playlistId, e);
            throw new SQLException("Error while fetching songs from playlist", e);
        }
        return songs;
    }

    @Override
    public void removeSongFromPlaylist(Long playlistId, Long songId) throws SQLException {
        String query = "DELETE FROM playlist_songs WHERE playlist_id = ? AND song_id = ?";
        LOGGER.info("Attempting to remove songId: {} from playlistId: {}", songId, playlistId);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, playlistId);
            statement.setLong(2, songId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("SongId: {} removed from playlistId: {}", songId, playlistId);
            } else {
                LOGGER.warn("SongId: {} not found in playlistId: {}", songId, playlistId);
            }
        } catch (SQLException e) {
            LOGGER.error("Error while removing songId: {} from playlistId: {}", songId, playlistId, e);
            throw new SQLException("Error while removing song from playlist", e);
        }
    }

    @Override
    public void removePlaylist(Long playlistId) throws SQLException {
        String query = "DELETE FROM playlists WHERE id = ?";
        LOGGER.info("Attempting to remove playlist with id: {}", playlistId);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, playlistId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("Playlist removed with id: {}", playlistId);
            } else {
                LOGGER.warn("Playlist not found with id: {}", playlistId);
            }
        } catch (SQLException e) {
            LOGGER.error("Error while removing playlist with id: {}", playlistId, e);
            throw new SQLException("Error while removing playlist", e);
        }
    }

    @Override
    public List<Playlist> getUserPlaylists(Long userId) throws SQLException {
        List<Playlist> playlists = new ArrayList<>();
        String query = "SELECT * FROM playlists WHERE user_id = ?";
        LOGGER.info("Fetching playlists for userId: {}", userId);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    playlists.add(mapRowToPlaylist(resultSet));
                }
                LOGGER.info("Fetched {} playlists for userId: {}", playlists.size(), userId);
            }
        } catch (SQLException e) {
            LOGGER.error("Error while fetching playlists for userId: {}", userId, e);
            throw new SQLException("Error while fetching user playlists", e);
        }
        return playlists;
    }

    private Playlist mapRowToPlaylist(ResultSet resultSet) throws SQLException {
        Playlist playlist = new Playlist();
        playlist.setId(resultSet.getLong("id"));
        playlist.setUserId(resultSet.getLong("user_id"));
        playlist.setTitle(resultSet.getString("title"));
        return playlist;
    }

    private Song mapRowToSong(ResultSet resultSet) throws SQLException {
        Song song = new Song();
        song.setId(resultSet.getLong("id"));
        song.setTitle(resultSet.getString("title"));
        song.setArtist(resultSet.getString("artist"));
        song.setDuration(resultSet.getInt("duration"));
        song.setUrl(resultSet.getString("url"));
        song.setCoverImage(resultSet.getString("cover_image"));
        song.setLyrics(resultSet.getString("lyrics"));
        return song;
    }
}
