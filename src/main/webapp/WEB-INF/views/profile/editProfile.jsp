<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-image: url('https://res.cloudinary.com/dkiovijcy/image/upload/v1733936454/IMG_4766_vsg2b5.jpg');
            background-size: cover;
            background-position: center;
            height: 100vh;
        }

        .edit-profile-container {
            max-width: 800px;
            margin: auto;
            padding: 30px;
            background-color: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .edit-profile-header {
            text-align: center;
            margin-bottom: 20px;
        }

        .btn-custom {
            background-color: rgba(102, 0, 153, 1);
            border-color: rgba(102, 0, 153, 1);
        }

        .btn-custom:hover {
            background-color: rgba(128, 0, 128, 1);
            border-color: rgba(128, 0, 128, 1);
        }

        .avatar-image {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .alert {
            margin-top: 20px;
        }
    </style>
</head>
<body>

<div class="container edit-profile-container">
    <div class="edit-profile-header">
        <h2>Edit Profile</h2>
        <img src="${user.avatarImage}" alt="Avatar" class="avatar-image">
    </div>

    <c:if test="${not empty message}">
        <div class="alert alert-danger text-center" role="alert">
                ${message}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/profile/edit" method="post" enctype="multipart/form-data">
        <input type="hidden" name="user_id" value="${user.id}" />

        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control" id="username" name="username" value="${user.username}" required />
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="email" value="${user.email}" required />
        </div>

        <div class="form-group">
            <label for="file">Avatar Image</label>
            <input type="file" class="form-control" id="file" name="file" accept="image/*" />
        </div>

        <div class="d-grid mt-4">
            <button type="submit" class="btn btn-custom">Save Changes</button>
        </div>
    </form>

    <div class="text-center mt-4">
        <button class="btn btn-secondary" onclick="window.location.href='${pageContext.request.contextPath}/profile'">Back to Profile</button>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
