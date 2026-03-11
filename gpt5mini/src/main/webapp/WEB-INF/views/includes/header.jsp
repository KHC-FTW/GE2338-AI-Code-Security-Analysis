<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav>
    <a href="${pageContext.request.contextPath}/">Home</a>
    <c:choose>
        <c:when test="${not empty sessionScope.username}">
            <span>Welcome, ${sessionScope.username}</span>
            <a href="${pageContext.request.contextPath}/profile">Profile</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/login">Login</a>
            <a href="${pageContext.request.contextPath}/register">Register</a>
        </c:otherwise>
    </c:choose>
    <form action="${pageContext.request.contextPath}/search" method="get" style="display:inline; margin-left:20px;">
        <input type="text" name="q" placeholder="Search..." />
        <button type="submit">Search</button>
    </form>
</nav>
<hr/>
