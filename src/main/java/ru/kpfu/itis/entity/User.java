package ru.kpfu.itis.entity;

import java.util.ArrayList;
import java.util.List;

public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String avatarImage; //не забыть что в бд есть default-image.png
    private List<Song> favoriteSongs;

    public User(String username, String email, String password, String avatarImage, List<Song> favoriteSongs) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatarImage = avatarImage;
        this.favoriteSongs = favoriteSongs;
    }

//    public User(String username, String email, String role, String avatarImage, List<Song> favoriteSongs) {
//        this.username = username;
//        this.email = email;
//        this.role = role;
//        this.avatarImage = avatarImage;
//        this.favoriteSongs = favoriteSongs;
//    }

    public User(String username, String email, String password, List<Song> favoriteSongs) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.favoriteSongs = favoriteSongs;
    }

    public User(Long id, String username, String email, String avatarImage) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatarImage = avatarImage;
    }

    public User(String username, String email, String password, String avatarImage) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatarImage = avatarImage;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatarImage = "default-avatar.png"; // например, аватар по умолчанию
        this.favoriteSongs = new ArrayList<>();
    }

    public User() {};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }

    public List<Song> getFavoriteSongs() {
        return favoriteSongs;
    }

    public void setFavoriteSongs(List<Song> favoriteSongs) {
        this.favoriteSongs = favoriteSongs;
    }

}
