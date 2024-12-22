package ru.kpfu.itis.servlet.playlist;

import com.cloudinary.Cloudinary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.entity.Playlist;
import ru.kpfu.itis.entity.Song;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.PlaylistService;
import ru.kpfu.itis.service.SongService;
import ru.kpfu.itis.util.CloudinaryUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebServlet("/playlist/create")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class CreatePlaylistServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(CreatePlaylistServlet.class);
    private PlaylistService playlistService;
    private SongService songService;
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.playlistService = new PlaylistService();
        this.songService = new SongService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Song> songs = songService.getAllSongs();
            req.setAttribute("songs", songs);
            getServletContext().getRequestDispatcher("/WEB-INF/views/createPlaylist.jsp").forward(req, resp);
        } catch (SQLException e) {
            LOG.error("Error fetching songs for playlist creation", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch songs from the database.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String playlistName = req.getParameter("name");
        String description = req.getParameter("description");
        Part coverImagePart = req.getPart("coverImage"); // Обложка плейлиста
        List<String> songIdsFromForm = Arrays.asList(req.getParameterValues("songIds")); // Список ID песен, которые выбраны для добавления

        if (songIdsFromForm == null || songIdsFromForm.isEmpty()) {
            req.setAttribute("error", "You must add at least one song to the playlist.");
            try {
                req.setAttribute("songs", songService.getAllSongs());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            getServletContext().getRequestDispatcher("/WEB-INF/views/createPlaylist.jsp").forward(req, resp);
            return;
        }

        String coverImageUrl = null;

        // Загружаем обложку плейлиста в Cloudinary
        if (coverImagePart != null && coverImagePart.getSize() > 0) {
            try {
                // Отправляем изображение на Cloudinary
                Map uploadResult = uploadImageToCloudinary(coverImagePart);

                // Получаем URL загруженного изображения
                coverImageUrl = (String) uploadResult.get("url");

            } catch (Exception e) {
                LOG.error("Error uploading cover image to Cloudinary", e);
                req.setAttribute("error", "Error uploading cover image. Please try again later.");
                try {
                    req.setAttribute("songs", songService.getAllSongs());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                getServletContext().getRequestDispatcher("/WEB-INF/views/createPlaylist.jsp").forward(req, resp);
                return;
            }
        }

        // Создаем новый плейлист
        Playlist playlist = new Playlist();
        playlist.setTitle(playlistName);
        playlist.setDescription(description);
        playlist.setCoverImage(coverImageUrl);
        playlist.setUserId(user.getId());

        try {
            // Сохраняем плейлист в базу данных
            playlistService.createPlaylist(playlist);

            // Получаем id нового плейлиста
            Long playlistId = playlist.getId();

            // Добавляем песни в новый плейлист
            for (String songIdStr : songIdsFromForm) {
                Long songId = Long.parseLong(songIdStr);

                // Проверяем, существует ли песня с таким ID в базе данных
                Song song = songService.getSongById(songId);
                if (song != null) {
                    // Добавляем песню в плейлист
                    songService.addSongToPlaylist(playlistId, songId);
                } else {
                    LOG.warn("Song with ID {} does not exist in the database and will not be added to the playlist.", songId);
                }
            }

            // Перенаправляем на страницу с плейлистами
            resp.sendRedirect(req.getContextPath() + "/playlists");
        } catch (SQLException | IOException e) {
            LOG.error("Error creating playlist", e);
            req.setAttribute("error", "Unable to create playlist. Please try again later.");
            try {
                req.setAttribute("songs", songService.getAllSongs());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            getServletContext().getRequestDispatcher("/WEB-INF/views/createPlaylist.jsp").forward(req, resp);
        }
    }

    // Метод для загрузки изображения в Cloudinary
    private Map uploadImageToCloudinary(Part coverImagePart) throws Exception {
        // Получаем InputStream изображения
        InputStream imageInputStream = coverImagePart.getInputStream();

        // Подготовка к загрузке в Cloudinary
        Map uploadResult = cloudinary.uploader().upload(imageInputStream, Map.of(
                "resource_type", "image"
        ));

        // Возвращаем результат загрузки
        return uploadResult;
    }
}
