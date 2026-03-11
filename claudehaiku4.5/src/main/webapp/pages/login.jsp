<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    pageContext.setAttribute("pageTitle", "Login");
%>
<jsp:include page="header.jsp" />

<div style="max-width: 400px; margin: 3rem auto;">
    <h1 style="text-align: center; margin-bottom: 2rem;">Login</h1>

    <form method="POST" action="${pageContext.request.contextPath}/auth">
        <input type="hidden" name="action" value="login">

        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit" class="btn btn-success" style="width: 100%;">Login</button>
    </form>

    <p style="text-align: center; margin-top: 1rem;">
        Don't have an account? <a href="${pageContext.request.contextPath}/auth?action=register">Register here</a>
    </p>
</div>

<jsp:include page="footer.jsp" />

