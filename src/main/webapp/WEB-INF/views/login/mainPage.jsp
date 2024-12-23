<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<%--<%@include file="/WEB-INF/tags/header.jsp" %>--%>

<%--<!-- Include navigation tag with the page title -->--%>

<%--    <!-- Main page layout -->--%>
<%--<style>--%>



<%--    .card {--%>
<%--        border-radius: 15px;--%>
<%--        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);--%>
<%--    }--%>

<%--    .card-header {--%>
<%--        background-color: rgba(102, 0, 153, 1) !important;--%>
<%--        color: white;--%>
<%--    }--%>

<%--    .btn-primary {--%>
<%--        background-color: rgba(102, 0, 153, 1);--%>
<%--        border-color: rgba(102, 0, 153, 1);--%>
<%--    }--%>

<%--    .btn-primary:hover {--%>
<%--        background-color: rgba(128, 0, 128, 1);--%>
<%--        border-color: rgba(128, 0, 128, 1);--%>
<%--    }--%>

<%--    .text-link {--%>
<%--        color: #ffffff;--%>
<%--        text-decoration: none;--%>
<%--    }--%>

<%--    .text-link:hover {--%>
<%--        text-decoration: underline;--%>
<%--    }--%>

<%--    .alert-warning {--%>
<%--        background-color: rgba(255, 193, 7, 0.9);--%>
<%--        color: white;--%>
<%--    }--%>

<%--    .play-btn {--%>
<%--        background-color: rgba(0, 123, 255, 1);--%>
<%--        border-color: rgba(0, 123, 255, 1);--%>
<%--    }--%>

<%--    .play-btn:hover {--%>
<%--        background-color: rgba(0, 102, 204, 1);--%>
<%--        border-color: rgba(0, 102, 204, 1);--%>
<%--    }--%>
<%--</style>--%>
<%--<body>--%>
<%--        <!-- Search form to search for songs -->--%>
<%--        <div class="container mt-5">--%>
<%--            <form method="get" action="/mainPage" class="d-flex mb-4">--%>
<%--                <input class="form-control" type="text" name="search" placeholder="Search songs..." aria-label="Search">--%>
<%--                <button class="btn btn-primary ml-2" type="submit">Search</button>--%>
<%--            </form>--%>

<%--            <!-- Display songs if they exist -->--%>
<%--            <div class="row">--%>
<%--                <c:forEach var="song" items="${songs}">--%>
<%--                    <div class="col-md-4 mb-4">--%>
<%--                        <div class="card">--%>
<%--                            <!-- Display song cover image -->--%>
<%--                            <img src="<c:url value='${song.coverImageUrl}'/>" class="card-img-top" alt="${song.title}">--%>
<%--                            <div class="card-body">--%>
<%--                                <!-- Song title and artist -->--%>
<%--                                <h5 class="card-title">${song.title}</h5>--%>
<%--                                <p class="card-text">${song.artist}</p>--%>

<%--                                <!-- If user is not logged in, disable certain actions -->--%>
<%--                                <c:if test="${empty user}">--%>
<%--                                    <a href="#" class="btn btn-secondary disabled" data-toggle="tooltip" title="You need to log in to add to favorites">Add to Favorites</a>--%>
<%--                                    <a href="#" class="btn btn-secondary disabled" data-toggle="tooltip" title="You need to log in to go to profile">Go to Profile</a>--%>
<%--                                </c:if>--%>

<%--                                <!-- If user is logged in, show options to add to favorites and go to profile -->--%>
<%--                                <c:if test="${not empty user}">--%>
<%--                                    <a href="/favorites/add?id=${song.id}" class="btn btn-warning">Add to Favorites</a>--%>
<%--                                    <a href="/profile" class="btn btn-info">Go to Profile</a>--%>
<%--                                </c:if>--%>

<%--                                <!-- Play button to play the song -->--%>
<%--                                <button class="btn btn-success mt-2 play-btn" data-audio-url="${song.audioUrl}" data-song-id="${song.id}">Play</button>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </c:forEach>--%>
<%--            </div>--%>
<%--        </div>--%>

<%--        <!-- Display message if user is not logged in -->--%>
<%--&lt;%&ndash;        <c:if test="${empty user}">&ndash;%&gt;--%>
<%--&lt;%&ndash;            <div class="alert alert-warning" role="alert">&ndash;%&gt;--%>
<%--&lt;%&ndash;                You are not logged in. Please log in to access additional features.&ndash;%&gt;--%>
<%--&lt;%&ndash;            </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;        </c:if>&ndash;%&gt;--%>

<%--        <!-- Controls for playing songs (play/pause/next/prev) -->--%>
<%--        <div class="container mt-4">--%>
<%--            <div id="player-controls">--%>
<%--                <button id="prev-song" class="btn btn-secondary">Prev</button>--%>
<%--                <button id="play-pause" class="btn btn-success">Play</button>--%>
<%--                <button id="next-song" class="btn btn-secondary">Next</button>--%>
<%--            </div>--%>
<%--            <p id="current-song-title"></p>--%>
<%--        </div>--%>


<%--        </body>--%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/tags/header.jsp" %>

<!-- Main page layout -->

<style>
    .card {
        border-radius: 15px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    .card-header {
        background-color: rgba(102, 0, 153, 1) !important;
        color: white;
    }

    .btn-primary {
        background-color: rgba(102, 0, 153, 1);
        border-color: rgba(102, 0, 153, 1);
    }

    .btn-primary:hover {
        background-color: rgba(128, 0, 128, 1);
        border-color: rgba(128, 0, 128, 1);
    }

    .text-link {
        color: #ffffff;
        text-decoration: none;
    }

    .text-link:hover {
        text-decoration: underline;
    }

    .alert-warning {
        background-color: rgba(255, 193, 7, 0.9);
        color: white;
    }

    .play-btn {
        background-color: rgba(0, 123, 255, 1);
        border-color: rgba(0, 123, 255, 1);
    }

    .play-btn:hover {
        background-color: rgba(0, 102, 204, 1);
        border-color: rgba(0, 102, 204, 1);
    }
</style>

<body>
    <!-- Search form to search for songs -->
    <div class="container mt-5">
        <form method="get" action="/mainPage" class="d-flex mb-4">
            <input class="form-control" type="text" name="search" placeholder="Search songs..." aria-label="Search">
            <button class="btn btn-primary ml-2" type="submit">Search</button>
        </form>

        <!-- Display songs if they exist -->
        <div class="row">
            <c:forEach var="song" items="${songs}">
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <!-- Display song cover image -->
                        <img src="<c:url value='${song.coverImageUrl}'/>" class="card-img-top" alt="${song.title}">
                        <div class="card-body">
                            <!-- Song title and artist -->
                            <h5 class="card-title">${song.title}</h5>
                            <p class="card-text">${song.artist}</p>

                            <!-- If user is not logged in, disable certain actions -->
                            <c:if test="${empty user}">
                                <a href="#" class="btn btn-secondary disabled" data-toggle="tooltip" title="You need to log in to add to favorites">Add to Favorites</a>
                                <a href="#" class="btn btn-secondary disabled" data-toggle="tooltip" title="You need to log in to go to profile">Go to Profile</a>
                            </c:if>

                            <!-- If user is logged in, show options to add to favorites and go to profile -->
                            <c:if test="${not empty user}">
                                <a href="/favorites/add?id=${song.id}" class="btn btn-warning">Add to Favorites</a>
                                <a href="/profile" class="btn btn-info">Go to Profile</a>
                            </c:if>

                            <!-- Play button to play the song -->
                            <button class="btn btn-success mt-2 play-btn" data-audio-url="${song.audioUrl}" data-song-id="${song.id}">Play</button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- Audio player and controls -->
    <div class="container mt-4">
        <!-- Audio player -->
        <audio id="audio-player" controls>
            <source id="audio-source" type="audio/mp3">
            Ваш браузер не поддерживает элемент audio.
        </audio>

        <!-- Player controls -->
        <div id="player-controls" class="mt-3">
            <button id="prev-song" class="btn btn-secondary">Prev</button>
            <button id="play-pause" class="btn btn-success">Play</button>
            <button id="next-song" class="btn btn-secondary">Next</button>
        </div>

        <p id="current-song-title">Now Playing: </p>
    </div>

    <!-- Include JavaScript file -->
    <script src="audioPlayer.js"></script>

</body>
