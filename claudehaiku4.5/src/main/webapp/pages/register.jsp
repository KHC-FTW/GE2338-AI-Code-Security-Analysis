<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    pageContext.setAttribute("pageTitle", "Register");
%>
<jsp:include page="header.jsp" />

<div style="max-width: 400px; margin: 3rem auto;">
    <h1 style="text-align: center; margin-bottom: 2rem;">Register</h1>

    <form method="POST" action="${pageContext.request.contextPath}/auth">
        <input type="hidden" name="action" value="register">

        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group">
            <label for="confirmPassword">Confirm Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>

        <button type="submit" class="btn btn-success" style="width: 100%;">Register</button>
    </form>

    <p style="text-align: center; margin-top: 1rem;">
        Already have an account? <a href="${pageContext.request.contextPath}/auth?action=login">Login here</a>
    </p>
</div>

<jsp:include page="footer.jsp" />

