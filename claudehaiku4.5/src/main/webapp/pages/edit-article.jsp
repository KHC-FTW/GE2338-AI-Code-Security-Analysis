<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    pageContext.setAttribute("pageTitle", "Edit Article");
%>
<jsp:include page="header.jsp" />

<c:choose>
    <c:when test="${not empty article}">
        <div style="max-width: 800px; margin: 0 auto;">
            <h1>Edit Article</h1>

            <form method="POST" action="${pageContext.request.contextPath}/article">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="${article.id}">

                <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text" id="title" name="title" value="${article.title}" required>
                </div>

                <div class="form-group">
                    <label for="content">Content</label>
                    <textarea id="content" name="content" required style="min-height: 400px;">${article.content}</textarea>
                </div>

                <button type="submit" class="btn btn-success">Update Article</button>
                <a href="${pageContext.request.contextPath}/article?action=view&id=${article.id}" class="btn">Cancel</a>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        <div style="text-align: center; padding: 2rem;">
            <p>Article not found or you don't have permission to edit it.</p>
            <a href="${pageContext.request.contextPath}/article?action=list" class="btn">Back to Articles</a>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="footer.jsp" />

