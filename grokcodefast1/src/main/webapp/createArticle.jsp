<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Create Article</title>
</head>
<body>
    <%@ include file="header.jsp" %>
    <h1>Create New Article</h1>
    <form action="createArticle" method="post">
        Title: <input type="text" name="title" required><br>
        Content: <textarea name="content" required></textarea><br>
        <input type="submit" value="Create">
    </form>
</body>
</html>
