<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp" %>
<h2>Search results</h2>
<c:if test="${not empty posts}">
    <ul>
        <c:forEach var="p" items="${posts}">
            <li><a href="${pageContext.request.contextPath}/post?slug=${p.slug}">${p.title}</a></li>
        </c:forEach>
    </ul>
</c:if>
<%@ include file="includes/footer.jsp" %>

