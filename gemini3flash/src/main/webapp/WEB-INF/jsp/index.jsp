<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>MyBlog - Home</title>
    <style>
        body { font-family: sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
        .container { max-width: 1000px; margin: 0 auto; padding: 1rem; background: white; min-height: 100vh; }
        .article-card { border-bottom: 1px solid #ddd; padding: 1rem 0; }
        .article-card:last-child { border-bottom: none; }
        .article-card h2 { margin-top: 0; }
        .meta { font-size: 0.85rem; color: #666; }
        .meta strong { font-weight: normal; color: #333; }
        .read-more { color: #007bff; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>
    <header>
        <%@ include file="common/header.jspf" %>
    </header>

    <div class="container">
        <c:if test="${not empty searchQuery}">
            <p>Showing search results for: <strong>${searchQuery}</strong></p>
        </c:if>

        <c:forEach var="article" items="${articles}">
            <div class="article-card">
                <h2><a href="article?id=${article.id}" style="color: black; text-decoration: none;">${article.title}</a></h2>
                <div class="meta">
                    By <strong>${article.authorName}</strong> on <span>${article.createdAt}</span>
                </div>
                <p>${article.content.length() > 200 ? article.content.substring(0, 200).concat("...") : article.content}</p>
                <a href="article?id=${article.id}" class="read-more">Read More &rarr;</a>
            </div>
        </c:forEach>

        <c:if test="${empty articles}">
            <p>No articles found.</p>
        </c:if>
    </div>
</body>
</html>

