<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Article - MyBlog</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f4f4; margin: 0; }
        .box { max-width: 800px; margin: 2rem auto; padding: 2rem; background: white; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .form-group { margin-bottom: 1rem; }
        .form-group label { display: block; margin-bottom: 0.5rem; }
        .form-group input, .form-group textarea { width: 100%; box-sizing: border-box; padding: 0.5rem; }
        .btn { padding: 0.5rem 1rem; background: #333; color: white; border: none; cursor: pointer; }
        .error { color: red; margin-bottom: 1rem; }
    </style>
</head>
<body>
    <%@ include file="common/header.jspf" %>
    <div class="box">
        <h2>Create New Article</h2>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <form action="create-article" method="post">
            <div class="form-group">
                <label>Title</label>
                <input type="text" name="title" required>
            </div>
            <div class="form-group">
                <label>Content</label>
                <textarea name="content" rows="15" required></textarea>
            </div>
            <button type="submit" class="btn">Publish Article</button>
        </form>
    </div>
</body>
</html>

