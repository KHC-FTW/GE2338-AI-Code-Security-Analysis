<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - MyBlog</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f4f4; margin: 0; }
        .login-box { max-width: 400px; margin: 5rem auto; padding: 2rem; background: white; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .form-group { margin-bottom: 1rem; }
        .form-group label { display: block; margin-bottom: 0.5rem; }
        .form-group input { width: 100%; box-sizing: border-box; padding: 0.5rem; }
        .btn { padding: 0.5rem 1rem; background: #333; color: white; border: none; cursor: pointer; }
        .error { color: red; margin-bottom: 1rem; }
        .success { color: green; margin-bottom: 1rem; }
    </style>
</head>
<body>
    <%@ include file="common/header.jspf" %>
    <div class="login-box">
        <h2>Login</h2>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${param.success == '1'}">
            <div class="success">Registration successful! Please login.</div>
        </c:if>

        <form action="login" method="post">
            <div class="form-group">
                <label>Username</label>
                <input type="text" name="username" required>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required>
            </div>
            <button type="submit" class="btn">Login</button>
        </form>
    </div>
</body>
</html>

