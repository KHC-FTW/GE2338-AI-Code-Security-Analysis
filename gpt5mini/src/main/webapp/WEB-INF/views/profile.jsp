<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp" %>
<h2>My Profile</h2>
<form method="post" action="${pageContext.request.contextPath}/profile">
    <label>Email: <input type="email" name="email" value="${user.email}"/></label><br/>
    <label>Display name: <input type="text" name="displayName" value="${user.displayName}"/></label><br/>
    <button type="submit">Save</button>
</form>
<%@ include file="includes/footer.jsp" %>

