<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
</head>
<body>
    <%@ include file="header.jsp" %>
    <h1>My Profile</h1>
    <form action="profile" method="post">
        Username: <input type="text" name="username" value="${username}" required><br>
        Email: <input type="email" name="email" value="${email}" required><br>
        Password: <input type="password" name="password" placeholder="New password" required><br>
        <input type="submit" value="Update">
    </form>
</body>
</html>
