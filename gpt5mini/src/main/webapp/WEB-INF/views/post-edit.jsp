<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp" %>
<h2>Create a new post</h2>
<c:if test="${not empty error}"><div style="color:red">${error}</div></c:if>
<form method="post" action="${pageContext.request.contextPath}/post-edit">
    <label>Title: <input type="text" name="title"/></label><br/>
    <label>Summary: <input type="text" name="summary"/></label><br/>
    <label>Content:<br/><textarea name="content" rows="10" cols="80"></textarea></label><br/>
    <label>Publish: <input type="checkbox" name="publish"/></label><br/>
    <button type="submit">Save</button>
</form>
<%@ include file="includes/footer.jsp" %>

