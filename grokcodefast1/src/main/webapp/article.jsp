<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
</head>
<body>
    <%@ include file="header.jsp" %>
    <h1>${title}</h1>
    <p>By ${author} on ${created_at}</p>
    <p>${content}</p>
    <h2>Comments</h2>
    <c:forEach var="comment" items="${comments}">
        <div>
            <p><strong>${comment.username}</strong> (${comment.created_at}): ${comment.content}</p>
        </div>
    </c:forEach>
    <c:if test="${sessionScope.username != null}">
        <h3>Add Comment</h3>
        <form action="comment" method="post">
            <input type="hidden" name="articleId" value="${articleId}">
            <textarea name="content" required></textarea><br>
            <input type="submit" value="Comment">
        </form>
    </c:if>
</body>
</html>
