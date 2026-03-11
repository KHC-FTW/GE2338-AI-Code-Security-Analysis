<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    pageContext.setAttribute("pageTitle", "Create Article");
%>
<jsp:include page="header.jsp" />

<c:choose>
    <c:when test="${not empty sessionScope.userId}">
        <div style="max-width: 800px; margin: 0 auto;">
            <h1>Create New Article</h1>

            <form method="POST" action="${pageContext.request.contextPath}/article">
                <input type="hidden" name="action" value="save">

                <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text" id="title" name="title" required>
                </div>

                <div class="form-group">
                    <label for="content">Content</label>
                    <textarea id="content" name="content" required style="min-height: 400px;"></textarea>
                </div>

                <button type="submit" class="btn btn-success">Publish Article</button>
                <a href="${pageContext.request.contextPath}/" class="btn">Cancel</a>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        <div style="text-align: center; padding: 2rem;">
            <p>Please <a href="${pageContext.request.contextPath}/auth?action=login">login</a> to create an article.</p>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="footer.jsp" />

