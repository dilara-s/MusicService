package ru.kpfu.itis.servlet.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.impl.UserDaoImpl;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService;
    private UserDaoImpl userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
        userDao = (UserDaoImpl) getServletContext().getAttribute("userDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            LOG.warn("Unauthorized access attempt to profile page.");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setAttribute("pageTitle", "Profile");

        try {
            User updatedUser = userDao.getUserById(user.getId());
            session.setAttribute("user", updatedUser);
        } catch (SQLException e) {
            req.setAttribute("message", "Error during download profile data.");
            LOG.error("Error while loading user profile data.", e);
        }

        String tab = req.getParameter("tab");
        if (tab == null || (!tab.equals("personal") && !tab.equals("account"))) {
            tab = "personal";
        }
        req.setAttribute("tab", tab);

        getServletContext().getRequestDispatcher("/WEB-INF/views/profile/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String action = req.getParameter("action");

        if (user == null) {
            LOG.warn("Unauthorized access attempt to delete account.");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if ("delete".equals(action)) {
            String confirm = req.getParameter("confirm");

            if ("yes".equals(confirm)) {
                try {
                    LOG.info("User {} is deleting their account.", user.getUsername());

                    userDao.deleteUser(user.getId());
                    userService.deleteUser(user, req, resp);

                    session.invalidate();
                    resp.sendRedirect(req.getContextPath() + "/main");
                    LOG.info("User {} account successfully deleted.", user.getUsername());

                } catch (SQLException e) {
                    req.setAttribute("message", "Error during deleting account. Try again later.");
                    LOG.error("Error while deleting user account.", e);
                    getServletContext().getRequestDispatcher("/WEB-INF/views/profile/profile.jsp").forward(req, resp);
                }
            } else {
                LOG.info("User {} canceled account deletion.", user.getUsername());
                resp.sendRedirect(req.getContextPath() + "/profile");
            }
        }
    }
}
