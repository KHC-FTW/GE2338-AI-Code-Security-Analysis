<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${article.title} - MyBlog</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f4f4; margin: 0; }
        .container { max-width: 800px; margin: 2rem auto; padding: 2rem; background: white; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .article-content { line-height: 1.6; margin-bottom: 3rem; }
        .meta { color: #666; font-size: 0.9rem; margin-bottom: 2rem; }
        .comment-section { border-top: 1px solid #ddd; padding-top: 2rem; }
        .comment { border-bottom: 1px dashed #ddd; padding: 1rem 0; }
        .comment:last-child { border-bottom: none; }
        .comment-meta { font-weight: bold; font-size: 0.9rem; }
        .comment-time { font-weight: normal; font-size: 0.8rem; color: #888; margin-left: 0.5rem; }
        .comment-form { margin-top: 2rem; }
        .comment-form textarea { width: 100%; box-sizing: border-box; padding: 0.5rem; }
        .btn { padding: 0.5rem 1rem; background: #333; color: white; border: none; cursor: pointer; margin-top: 0.5rem; }
    </style>
</head>
<body>
    <%@ include file="common/header.jspf" %>
    <div class="container">
        <h1>${article.title}</h1>
        <div class="meta">
            By <strong>${article.authorName}</strong> on <span>${article.createdAt}</span>
        </div>
        <div class="article-content">
            ${article.content.replace("\n", "<br/>")}
        </div>

        <div class="comment-section">
            <h3>Comments (${comments.size()})</h3>
            <c:forEach var="comment" items="${comments}">
                <div class="comment">
                    <div class="comment-meta">
                        ${comment.username}
                        <span class="comment-time">${comment.createdAt}</span>
                    </div>
                    <div class="comment-content">
                        ${comment.content}
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty comments}">
                <p>No comments yet. Be the first to comment!</p>
            </c:if>

            <div class="comment-form">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <h4>Leave a Comment</h4>
                        <form action="article" method="post">
                            <input type="hidden" name="articleId" value="${article.id}">
                            <textarea name="content" rows="4" required></textarea>
                            <button type="submit" class="btn">Post Comment</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <p><a href="login">Login</a> to leave a comment.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>
</html>

