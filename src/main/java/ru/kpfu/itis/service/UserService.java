package ru.kpfu.itis.service;

import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.dao.impl.UserDaoImpl;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.util.PasswordUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.sql.SQLException;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(User user) throws SQLException {
        if (userDao.isUserExists(user.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists.");
        }

        validatePassword(user.getPassword());
        String hashedPassword = PasswordUtil.encrypt(user.getPassword());
        user.setPassword(hashedPassword);

        userDao.addUser(user);
    }

    private void validatePassword(String password) throws ValidationException {
        if (password == null || password.length() < 6) {
            throw new ValidationException("Password must be at least 6 characters long.");
        }
    }

    public void authUser(User user, HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        req.getSession().setAttribute("user", user);
    }

    public User getUserByEmail(String email) throws SQLException {
        return userDao.getUserByEmail(email);
    }

    public User getUserById(Long id) throws SQLException {
        return userDao.getUserById(id);
    }

    public void updateUser(User user) throws SQLException {
        userDao.updateUser(user);
    }

    public boolean updatePassword(User user, String newPassword) throws SQLException {
        user.setPassword(newPassword);
        return userDao.updatePassword(user);
    }

    public void deleteUser(User user, HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        req.getSession().removeAttribute("user");
    }

    public boolean isUserExists(String email) throws SQLException {
        return userDao.isUserExists(email);
    }

    public void logout(HttpServletRequest req) {
        if (req.getSession() != null) {
            req.getSession().invalidate();
        }
    }
}
