<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search</title>
</head>
<body>
    <%@ include file="header.jsp" %>
    <h1>Search Articles</h1>
    <form action="search" method="get">
        <input type="text" name="q" value="${query}" required>
        <input type="submit" value="Search">
    </form>
    <c:if test="${not empty results}">
        <h2>Results</h2>
        <c:forEach var="result" items="${results}">
            <div>
                <h3><a href="article?id=${result.id}">${result.title}</a></h3>
            </div>
        </c:forEach>
    </c:if>
</body>
</html>
