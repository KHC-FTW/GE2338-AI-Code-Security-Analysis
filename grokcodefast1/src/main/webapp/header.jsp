<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
    <nav>
        <ul>
            <li><a href="index.jsp">Home</a></li>
            <li><a href="search.jsp">Search</a></li>
            <c:if test="${sessionScope.username == null}">
                <li><a href="login.jsp">Login</a></li>
                <li><a href="register.jsp">Register</a></li>
            </c:if>
            <c:if test="${sessionScope.username != null}">
                <li><a href="profile.jsp">Profile</a></li>
                <li><a href="createArticle.jsp">Create Article</a></li>
                <li><a href="logout">Logout</a></li>
            </c:if>
        </ul>
    </nav>
</header>
