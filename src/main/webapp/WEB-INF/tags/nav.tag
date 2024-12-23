<%--<%@tag description="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@attribute name="title" required="true" %>>--%>
<%--<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>--%>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>


<%--<t:mainLayout title="${title}">--%>

<%--    <!-- Вставляем навигационную панель -->--%>

<%--    <div class="container mt-5">--%>
<%--        <!-- Главная панель -->--%>
<%--        <div class="d-flex justify-content-between">--%>
<%--            <h1 class="mb-4">${title}</h1>--%>

<%--            <!-- Если пользователь авторизован, показываем кнопку Upload Song -->--%>
<%--            <c:if test="${not empty user}">--%>
<%--                <a href="/upload" class="btn btn-primary mt-2">Upload Song</a>--%>
<%--            </c:if>--%>
<%--        </div>--%>

<%--        <!-- Панель поиска -->--%>
<%--        <form method="get" action="/search" class="d-flex mb-4">--%>
<%--            <input class="form-control" type="text" name="query" placeholder="Поиск песен..." aria-label="Search">--%>
<%--            <button class="btn btn-primary ml-2" type="submit">Поиск</button>--%>
<%--        </form>--%>

<%--        <!-- Список песен -->--%>
<%--        <div class="row">--%>
<%--            <c:forEach var="song" items="${songs}">--%>
<%--                <div class="col-md-4 mb-4">--%>
<%--                    <div class="card">--%>
<%--                        <img src="<c:url value='${song.coverImageUrl}'/>" class="card-img-top" alt="${song.title}">--%>
<%--                        <div class="card-body">--%>
<%--                            <h5 class="card-title">${song.title}</h5>--%>
<%--                            <p class="card-text">${song.artist}</p>--%>

<%--                            <!-- Если пользователь не авторизован, показываем ограниченный функционал -->--%>
<%--                            <c:if test="${empty user}">--%>
<%--                                <a href="#" class="btn btn-secondary disabled" data-toggle="tooltip" title="Вы не вошли в аккаунт">Добавить в избранное</a>--%>
<%--                                <a href="#" class="btn btn-secondary disabled" data-toggle="tooltip" title="Вы не вошли в аккаунт">Перейти в профиль</a>--%>
<%--                            </c:if>--%>

<%--                            <!-- Если пользователь авторизован, показываем возможность добавить в избранное -->--%>
<%--                            <c:if test="${not empty user}">--%>
<%--                                <a href="/favorites/add?id=${song.id}" class="btn btn-warning">Добавить в избранное</a>--%>
<%--                                <a href="/profile" class="btn btn-info">Перейти в профиль</a>--%>
<%--                            </c:if>--%>

<%--                            <!-- Кнопка воспроизведения песни -->--%>
<%--                            <button class="btn btn-success mt-2 play-btn" data-audio-url="${song.audioUrl}" data-song-id="${song.id}">Play</button>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </c:forEach>--%>
<%--        </div>--%>
<%--    </div>--%>

<%--    <!-- Сообщение, если пользователь не авторизован -->--%>
<%--    <c:if test="${empty user}">--%>
<%--        <div class="alert alert-warning" role="alert">--%>
<%--            Вы не вошли в аккаунт. Пожалуйста, войдите, чтобы получить доступ к дополнительным возможностям.--%>
<%--        </div>--%>
<%--    </c:if>--%>

<%--    <!-- Панель управления воспроизведением -->--%>
<%--    <div class="container mt-4">--%>
<%--        <div id="player-controls">--%>
<%--            <button id="prev-song" class="btn btn-secondary">Prev</button>--%>
<%--            <button id="play-pause" class="btn btn-success">Play</button>--%>
<%--            <button id="next-song" class="btn btn-secondary">Next</button>--%>
<%--        </div>--%>
<%--        <p id="current-song-title"></p>--%>
<%--    </div>--%>
<%--    <jsp:doBody/>--%>
<%--</t:mainLayout>--%>
