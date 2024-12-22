package ru.kpfu.itis.service;

import ru.kpfu.itis.dao.SongDao;
import ru.kpfu.itis.entity.Song;

import java.sql.SQLException;
import java.util.List;

public class SongService {

    private SongDao songDao;

    public SongService() {
        this.songDao = songDao;
    }

    public void addSong(Song song) throws SQLException{
        if (songDao.isSongExists(song.getTitle(), song.getArtist())) {
            throw new IllegalArgumentException("Песня с таким названием и артистом уже существует.");
        }
        songDao.addSong(song);
    }

    public Song getSongById(Long id) throws SQLException {
        return songDao.getSongById(id);
    }

    public void updateSong(Song song) throws SQLException{
        if (song.getId() == null) {
            throw new IllegalArgumentException("ID песни не может быть null.");
        }
        songDao.updateSong(song);
    }

    public void deleteSong(Long id) throws SQLException{
        Song song = songDao.getSongById(id);
        if (song == null) {
            throw new IllegalArgumentException("Песня с таким ID не найдена.");
        }
        songDao.deleteSong(id);
    }

    public List<Song> getAllSongs() throws SQLException{
        return songDao.getAllSongs();
    }

    public List<Song> searchSongs(String query) throws SQLException{
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Поисковый запрос не может быть пустым.");
        }
        return songDao.searchSongs(query);
    }

    public void addSongToPlaylist(Long playlistId, Long songId) throws SQLException{
        songDao.addSongToPlaylist(playlistId, songId);
    }


    public List<Song> getSongsByPlaylistId(Long playlistId) throws SQLException {
        return songDao.getSongsByPlaylistId(playlistId);
    }

}
