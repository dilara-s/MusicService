<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-image: url('https://res.cloudinary.com/dkiovijcy/image/upload/v1733936454/IMG_4766_vsg2b5.jpg');
            background-size: cover;
            background-position: center;
            height: 100vh;
        }
        .login-container {
            max-width: 400px;
            margin: auto;
            padding: 30px;
            background-color: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .login-title {
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>

    <style>
        body {
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            position: relative;
            color: white;
            margin: 0;
            background-color: #f0f0f0;
        }

        body::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: url('https://avatars.mds.yandex.net/i?id=43eaf4c2fd08013f09a1f1a2923a6c00_l-5603028-images-thumbs&n=13') no-repeat center center fixed;
            background-size: cover;
            filter: blur(10px);
            z-index: -1;
        }

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
            color: #660099FF;
            text-decoration: none;
        }

        .text-link:hover {
            text-decoration: underline;
        }
    </style>

    <div class="container d-flex justify-content-center align-items-center">
        <div class="card p-4 col-md-4">
            <div class="card-header text-center">
                <h3>Register</h3>
            </div>
            <div class="card-body">
                <!-- Display messages -->
                <c:if test="${not empty message}">
                    <div class="alert alert-info">
                            ${message}
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/register" method="post">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" name="username" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary">Register</button>
                    </div>
                </form>
                <div class="mt-3 text-center">
                    <a href="${pageContext.request.contextPath}/login" class="text-link">Already have an account? Login</a>
                </div>
            </div>
        </div>
    </div>

