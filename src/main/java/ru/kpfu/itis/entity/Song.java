package ru.kpfu.itis.entity;

public class Song {
    private Long id;
    private String title;
    private String artist;
    private int duration;
    private String url;//для подгрузки из клаудинари
    private String coverImage;
    private String lyrics;

    public Song(String title, String artist, int duration, String url, String coverImage, String lyrics) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.url = url;
        this.coverImage = coverImage;
        this.lyrics = lyrics;
    }

    public Song(String title, String artist, int duration, String url, String coverImage) {
        this(title, artist, duration, url, coverImage, "");
    }

    public Song() {}

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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}