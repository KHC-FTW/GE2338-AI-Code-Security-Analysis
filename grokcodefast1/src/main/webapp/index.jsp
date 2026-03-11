<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Blog Home</title>
</head>
<body>
    <%@ include file="header.jsp" %>
    <h1>Welcome to the Blog</h1>
    <%
        List<Map<String, Object>> articles = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/blogdb", "user", "password");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, title, created_at FROM articles ORDER BY created_at DESC");
            while (rs.next()) {
                Map<String, Object> article = new HashMap<>();
                article.put("id", rs.getInt("id"));
                article.put("title", rs.getString("title"));
                article.put("created_at", rs.getTimestamp("created_at"));
                articles.add(article);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("articles", articles);
    %>
    <c:forEach var="article" items="${articles}">
        <div>
            <h2><a href="article?id=${article.id}">${article.title}</a></h2>
            <p>${article.created_at}</p>
        </div>
    </c:forEach>
</body>
</html>
