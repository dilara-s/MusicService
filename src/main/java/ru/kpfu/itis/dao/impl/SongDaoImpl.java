package ru.kpfu.itis.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.SongDao;
import ru.kpfu.itis.entity.Song;
import ru.kpfu.itis.util.DatabaseCollectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDaoImpl implements SongDao {

    private static final Logger logger = LoggerFactory.getLogger(SongDaoImpl.class);
    private Connection connection;

    public SongDaoImpl() {
        this.connection = DatabaseCollectionUtil.getConnection();
    }

    @Override
    public void addSong(Song song) {
        String query = "INSERT INTO songs (title, artist, duration, url, cover_image, lyrics) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, song.getTitle());
            statement.setString(2, song.getArtist());
            statement.setInt(3, song.getDuration());
            statement.setString(4, song.getUrl());
            statement.setString(5, song.getCoverImage());
            statement.setString(6, song.getLyrics());

            statement.executeUpdate();
            logger.info("Song added successfully: {}", song.getTitle());
        } catch (SQLException e) {
            logger.error("Error while adding song: {}", song.getTitle(), e);
        }
    }

    @Override
    public Song getSongById(Long id) {
        String query = "SELECT * FROM songs WHERE id = ?";
        Song song = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    song = mapRowToSong(resultSet);
                    logger.info("Song found: {}", song.getTitle());
                }
            }
        } catch (SQLException e) {
            logger.error("Error while retrieving song with id: {}", id, e);
        }
        return song;
    }

    @Override
    public void updateSong(Song song) {
        String query = "UPDATE songs SET title = ?, artist = ?, duration = ?, url = ?, cover_image = ?, lyrics = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, song.getTitle());
            statement.setString(2, song.getArtist());
            statement.setInt(3, song.getDuration());
            statement.setString(4, song.getUrl());
            statement.setString(5, song.getCoverImage());
            statement.setString(6, song.getLyrics());
            statement.setLong(7, song.getId());

            statement.executeUpdate();
            logger.info("Song updated successfully: {}", song.getTitle());
        } catch (SQLException e) {
            logger.error("Error while updating song: {}", song.getTitle(), e);
        }
    }

    @Override
    public void deleteSong(Long id) {
        String query = "DELETE FROM songs WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            logger.info("Song deleted successfully, id: {}", id);
        } catch (SQLException e) {
            logger.error("Error while deleting song with id: {}", id, e);
        }
    }

    @Override
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT * FROM songs";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                songs.add(mapRowToSong(resultSet));
            }
            logger.info("Retrieved {} songs from the database.", songs.size());
        } catch (SQLException e) {
            logger.error("Error while retrieving all songs.", e);
        }
        return songs;
    }

    @Override
    public boolean isSongExists(String title, String artist) {
        String query = "SELECT COUNT(*) FROM songs WHERE title = ? AND artist = ?";
        boolean exists = false;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, title);
            statement.setString(2, artist);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    exists = true;
                    logger.info("Song with title '{}' and artist '{}' exists in the database.", title, artist);
                } else {
                    logger.info("Song with title '{}' and artist '{}' does not exist in the database.", title, artist);
                }
            }
        } catch (SQLException e) {
            logger.error("Error while checking if song exists: {} - {}", title, artist, e);
        }
        return exists;
    }

    @Override
    public List<Song> searchSongs(String query) {
        List<Song> songs = new ArrayList<>();
        String sqlQuery = "SELECT * FROM songs WHERE title LIKE ? OR artist LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            String searchQuery = "%" + query + "%";
            statement.setString(1, searchQuery);
            statement.setString(2, searchQuery);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    songs.add(mapRowToSong(resultSet));
                }
            }
            logger.info("Found {} songs matching the query '{}'.", songs.size(), query);
        } catch (SQLException e) {
            logger.error("Error while searching for songs with query: {}", query, e);
        }
        return songs;
    }

    @Override
    public void addSongToPlaylist(Long playlistId, Long songId) {
        String query = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, playlistId);
            statement.setLong(2, songId);
            statement.executeUpdate();
            logger.info("Song with id {} added to playlist with id {}.", songId, playlistId);
        } catch (SQLException e) {
            logger.error("Error while adding song with id {} to playlist with id {}", songId, playlistId, e);
        }
    }

    @Override
    public List<Song> getSongsByPlaylistId(Long playlistId) throws SQLException {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT s.id, s.title, s.artist, s.url " +
                "FROM songs s " +
                "JOIN playlist_songs ps ON s.id = ps.song_id " +
                "WHERE ps.playlist_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Song song = new Song();
                song.setId(resultSet.getLong("id"));
                song.setTitle(resultSet.getString("title"));
                song.setArtist(resultSet.getString("artist"));
                song.setUrl(resultSet.getString("url"));
                songs.add(song);
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching songs for playlist with ID: " + playlistId, e);
        }
        return songs;
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
