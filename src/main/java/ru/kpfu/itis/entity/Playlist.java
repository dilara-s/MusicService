package ru.kpfu.itis.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Playlist {
    private Long id;
    private String title;
    private String description;
    private String coverImage;
    private List<Song> tracks;
    private Long userId;//кому принадлежит (надо ли?)

    public Playlist() {}

    public Playlist(String title, String description, String coverImage, List<Song> tracks, Long userId) {
        this.title = title;
        this.description = description;
        this.coverImage = coverImage;
        this.tracks = tracks;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<Song> getTracks() {
        return tracks;
    }

    public void setTracks(List<Song> tracks) {
        this.tracks = tracks;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
