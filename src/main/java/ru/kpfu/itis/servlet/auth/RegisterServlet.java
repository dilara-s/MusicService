package ru.kpfu.itis.servlet.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.impl.UserDaoImpl;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.util.PasswordUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(RegisterServlet.class);
    private UserDaoImpl userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao = (UserDaoImpl) getServletContext().getAttribute("userDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if(username == null || password == null || email == null) {
            req.setAttribute("message", "All fields are required");
            getServletContext().getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
            return;
        }

        try {
            String encryptedPassword = PasswordUtil.encrypt(password);

            User user = userDao.getUsernameAndPassword(username, encryptedPassword);
            if (user != null) {
                req.setAttribute("message", "User already exists");
                getServletContext().getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
            } else {
                User newUser = new User(username, encryptedPassword, email);
                userDao.addUser(newUser);

                req.setAttribute("message", "Registration successful");
                resp.sendRedirect(req.getContextPath() + "/mainPage");
            }
        } catch (SQLException e) {
            req.setAttribute("message", "Error during registration. Try again.");
            getServletContext().getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
        }
    }

}
