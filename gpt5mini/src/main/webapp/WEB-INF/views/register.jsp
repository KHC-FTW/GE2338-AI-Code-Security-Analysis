<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp" %>
<h2>Register</h2>
<c:if test="${not empty error}"><div style="color:red">${error}</div></c:if>
<form method="post" action="${pageContext.request.contextPath}/register">
    <label>Username: <input type="text" name="username"/></label><br/>
    <label>Email: <input type="email" name="email"/></label><br/>
    <label>Password: <input type="password" name="password"/></label><br/>
    <label>Display name: <input type="text" name="displayName"/></label><br/>
    <button type="submit">Register</button>
</form>
<%@ include file="includes/footer.jsp" %>

