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

        .profile-container {
            max-width: 800px;
            margin: auto;
            padding: 30px;
            background-color: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .profile-header {
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

        .btn-danger {
            background-color: red;
            border-color: red;
        }

        .btn-danger:hover {
            background-color: darkred;
            border-color: darkred;
        }

        .avatar-image {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            margin-bottom: 20px;
        }

        .tab-content {
            margin-top: 20px;
        }

        .tab-content .tab-pane {
            padding: 20px;
            border-top: 2px solid #ccc;
        }

    </style>
</head>
<body>

<div class="container profile-container">
    <div class="profile-header">
        <h2>Welcome, ${user.username}!</h2>
        <img src="${user.avatarImage}" alt="Avatar" class="avatar-image">
    </div>

    <div class="text-center mb-4">
        <button class="btn btn-custom" onclick="window.location.href='${pageContext.request.contextPath}/playlist'">Playlists</button>
        <button class="btn btn-custom" onclick="window.location.href='${pageContext.request.contextPath}/favourites'">Favourite Songs</button>
        <button class="btn btn-custom" onclick="window.location.href='${pageContext.request.contextPath}/editProfile'">Edit Profile</button>
    </div>

    <!-- Delete Account Button -->
    <div class="text-center mb-4">
        <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">Delete Account</button>
    </div>

    <!-- Modal for account deletion confirmation -->
    <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteModalLabel">Confirm Account Deletion</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    Are you sure you want to delete your account? This action is irreversible.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <form action="${pageContext.request.contextPath}/profile" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="confirm" value="yes">
                        <button type="submit" class="btn btn-danger">Yes, Delete</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="tab-content">
        <div class="tab-pane active" id="personal">
            <h4>Personal Information</h4>
            <p><strong>Email:</strong> ${user.email}</p>
            <p><strong>Username:</strong> ${user.username}</p>
        </div>
        <div class="tab-pane" id="account">
            <h4>Account Settings</h4>
            <p><strong>Password:</strong> ********</p> <!-- Don't show password -->
            <p><strong>Avatar:</strong> <img src="${user.avatarImage}" alt="Avatar" class="avatar-image"></p>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
