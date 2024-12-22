package ru.kpfu.itis.servlet.song;

import ru.kpfu.itis.entity.Song;
import ru.kpfu.itis.service.SongService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/songDetails")
public class SongServlet extends HttpServlet {

    private SongService songService;

    @Override
    public void init() throws ServletException {
        songService = new SongService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long songId = Long.parseLong(req.getParameter("id"));

        try {
            Song song = songService.getSongById(songId);

            if (song == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Song not found");
                return;
            }

            req.setAttribute("song", song);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/songDetails.jsp");
            dispatcher.forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long songId = Long.parseLong(req.getParameter("id"));

        try {
            songService.deleteSong(songId);

            resp.sendRedirect(req.getContextPath() + "/songs");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        }
    }
}
