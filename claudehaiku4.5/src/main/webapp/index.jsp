<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    pageContext.setAttribute("pageTitle", "Home");
%>
<jsp:include page="header.jsp" />

<div style="text-align: center; padding: 2rem; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border-radius: 8px; margin-bottom: 2rem;">
    <h1 style="font-size: 2.5rem; margin-bottom: 1rem;">Welcome to MyBlog</h1>
    <p style="font-size: 1.2rem; margin-bottom: 1rem;">Share your thoughts and ideas with the world</p>
    <c:choose>
        <c:when test="${empty sessionScope.userId}">
            <a href="${pageContext.request.contextPath}/auth?action=login" class="btn" style="background-color: white; color: #667eea; margin-right: 1rem;">Login</a>
            <a href="${pageContext.request.contextPath}/auth?action=register" class="btn" style="background-color: white; color: #667eea;">Register</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/article?action=create" class="btn" style="background-color: white; color: #667eea;">Write an Article</a>
            <a href="${pageContext.request.contextPath}/article?action=list" class="btn" style="background-color: white; color: #667eea; margin-left: 1rem;">Read Articles</a>
        </c:otherwise>
    </c:choose>
</div>

<h2>Latest Articles</h2>
<jsp:include page="articles-list.jsp" />

<jsp:include page="footer.jsp" />

