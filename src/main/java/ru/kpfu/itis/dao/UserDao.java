package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.Song;
import ru.kpfu.itis.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    public void addUser(User user) throws SQLException;
    public User getUsernameAndPassword(String username, String password) throws SQLException;
    public User getUserById(Long id) throws SQLException;
    public Long getUserId(String username) throws SQLException;
    public User getUserByEmail(String email) throws SQLException;
    public boolean updateUser(User user) throws SQLException;
    public boolean updatePassword(User user) throws SQLException;
    public void deleteUser(Long id) throws SQLException;
    public boolean isUserExists(String email) throws SQLException;
}
