package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.Playlist;
import ru.kpfu.itis.entity.Song;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PlaylistDao {
    public Connection getConnection();
    public void addPlaylist(Playlist playlist) throws SQLException;
    public void addSongToPlaylist(Long playlistId, Long songId) throws SQLException;//с проверкой что пенся в избранном
    public Playlist getPlaylistById(Long id) throws SQLException;
    public List<Song> getSongsByPlaylistId(Long playlistId) throws SQLException;
    public void removeSongFromPlaylist(Long playlistId, Long songId) throws SQLException;
    public void removePlaylist(Long playlistId) throws SQLException;
    public List<Playlist> getUserPlaylists(Long userId) throws SQLException;
}
