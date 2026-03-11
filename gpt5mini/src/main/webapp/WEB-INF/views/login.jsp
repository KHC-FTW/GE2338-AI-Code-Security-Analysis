<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp" %>
<h2>Login</h2>
<c:if test="${not empty error}"><div style="color:red">${error}</div></c:if>
<form method="post" action="${pageContext.request.contextPath}/login">
    <label>Username or Email: <input type="text" name="user"/></label><br/>
    <label>Password: <input type="password" name="password"/></label><br/>
    <button type="submit">Login</button>
</form>
<%@ include file="includes/footer.jsp" %>

