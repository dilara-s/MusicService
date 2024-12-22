package ru.kpfu.itis.servlet.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.entity.Song;
import ru.kpfu.itis.service.SongService;
import ru.kpfu.itis.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/mainPage")
public class MainPageServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainPageServlet.class);

    private SongService songService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        songService = (SongService) getServletContext().getAttribute("songService");
        userService = (UserService) getServletContext().getAttribute("userService");
        LOGGER.info("MainPageServlet initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long userId = (Long) req.getSession().getAttribute("user_id");

            String searchQuery = req.getParameter("search");

            if (searchQuery != null && !searchQuery.isEmpty()) {
                LOGGER.info("Search query received: {}", searchQuery);
            } else {
                LOGGER.info("No search query received.");
            }

            List<Song> songs;

            if (searchQuery != null && !searchQuery.isEmpty()) {
                songs = songService.searchSongs(searchQuery);
                req.setAttribute("searchQuery", searchQuery);
                LOGGER.info("Found {} songs for search query: {}", songs.size(), searchQuery);
            } else {
                songs = songService.getAllSongs();
                LOGGER.info("Displaying all {} songs.", songs.size());
            }

            req.setAttribute("songs", songs);

            if (userId == null) {
                req.setAttribute("authError", "Вы не вошли в аккаунт.");
                LOGGER.warn("User is not logged in. Redirecting to login page.");
            // Для неавторизованных пользователей скрываем доступ к кнопке загрузки и добавлению в избранное
                req.setAttribute("canUpload", false);
                req.setAttribute("canAddToFavorites", false);
            } else {
            // Для авторизованных пользователей предоставляем возможность загрузки и добавления в избранное
            req.setAttribute("canUpload", true);
            req.setAttribute("canAddToFavorites", true);
        }

            req.setAttribute("userId", userId);

            getServletContext().getRequestDispatcher("/WEB-INF/views/login/mainPage.jsp").forward(req, resp);
        } catch (SQLException e) {
            LOGGER.error("Error while fetching songs.", e);
            throw new ServletException("Error while fetching songs", e);
        }
    }
}
