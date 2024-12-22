package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.Song;

import java.sql.SQLException;
import java.util.List;

public interface SongDao {
    public void addSong(Song song) throws SQLException;

    public Song getSongById(Long id) throws SQLException;

    public void updateSong(Song song) throws SQLException;

    public void deleteSong(Long id) throws SQLException;

    public List<Song> getAllSongs() throws SQLException;

    public boolean isSongExists(String title, String artist) throws SQLException;

    public List<Song> searchSongs(String query) throws SQLException;

    public void addSongToPlaylist(Long playlistId, Long songId) throws SQLException;

    public List<Song> getSongsByPlaylistId(Long playlistId) throws SQLException;
}
