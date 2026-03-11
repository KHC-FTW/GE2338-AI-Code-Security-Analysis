<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    pageContext.setAttribute("pageTitle", "Articles");
%>
<jsp:include page="header.jsp" />

<h1>All Articles</h1>

<c:choose>
    <c:when test="${not empty articles}">
        <c:forEach var="article" items="${articles}">
            <div class="article-card">
                <h3 class="article-title">
                    <a href="${pageContext.request.contextPath}/article?action=view&id=${article.id}" style="color: #2c3e50; text-decoration: none;">
                        ${article.title}
                    </a>
                </h3>
                <div class="article-meta">
                    By <strong>${article.authorUsername}</strong> |
                    <fmt:formatDate value="${article.createdAt}" pattern="MMM dd, yyyy hh:mm a"/>
                </div>
                <div class="article-excerpt">
                    ${article.content.length() > 200 ? article.content.substring(0, 200).concat('...') : article.content}
                </div>
                <a href="${pageContext.request.contextPath}/article?action=view&id=${article.id}" class="btn btn-primary">Read More</a>
            </div>
        </c:forEach>

        <!-- Pagination -->
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/article?action=list&page=1">First</a>
                <a href="${pageContext.request.contextPath}/article?action=list&page=${currentPage - 1}">Previous</a>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="pageNum">
                <c:choose>
                    <c:when test="${pageNum == currentPage}">
                        <span>${pageNum}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/article?action=list&page=${pageNum}">${pageNum}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <a href="${pageContext.request.contextPath}/article?action=list&page=${currentPage + 1}">Next</a>
                <a href="${pageContext.request.contextPath}/article?action=list&page=${totalPages}">Last</a>
            </c:if>
        </div>
    </c:when>
    <c:otherwise>
        <div style="text-align: center; padding: 2rem; background: #f9f9f9; border-radius: 4px;">
            <p>No articles found. <a href="${pageContext.request.contextPath}/article?action=create">Create one now!</a></p>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="footer.jsp" />

