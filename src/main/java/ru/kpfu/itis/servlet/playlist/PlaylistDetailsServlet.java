package ru.kpfu.itis.servlet.playlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.impl.PlaylistDaoImpl;
import ru.kpfu.itis.dao.impl.SongDaoImpl;
import ru.kpfu.itis.entity.Playlist;
import ru.kpfu.itis.entity.Song;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.PlaylistService;
import ru.kpfu.itis.service.SongService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/playlists/playlistDetails")
public class PlaylistDetailsServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(PlaylistDetailsServlet.class);
    private PlaylistService playlistService;
    private SongService songService;
    private SongDaoImpl songDao;
    private PlaylistDaoImpl playlistDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.playlistService = new PlaylistService(playlistDao);
        this.songService = new SongService(songDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Long playlistId = Long.parseLong(req.getParameter("id"));

        try {
            Playlist playlist = playlistService.getPlaylistById(playlistId);
            List<Song> songs = songService.getSongsByPlaylistId(playlistId);

            req.setAttribute("playlist", playlist);
            req.setAttribute("songs", songs);

            getServletContext().getRequestDispatcher("/WEB-INF/views/profile/playlist/playlistDetails.jsp").forward(req, resp);

        } catch (SQLException e) {
            LOG.error("Error fetching playlist details for playlistId {}", playlistId, e);
            req.setAttribute("error", "Unable to load playlist details. Please try again later.");
            getServletContext().getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

}
