<%@ page import="cityu.khchan744.dao.ArticleDAO" %>
<%@ page import="cityu.khchan744.model.Article" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if (request.getAttribute("articles") == null) {
        ArticleDAO articleDAO = new ArticleDAO();
        try {
            List<Article> articles = articleDAO.getAllArticles(5, 0);
            request.setAttribute("articles", articles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>

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
                    <c:set var="createdDate" value="${article.createdAt}" />
                    <fmt:formatDate value="${createdDate}" pattern="MMM dd, yyyy hh:mm a" />
                </div>
                <div class="article-excerpt">
                    ${article.content.length() > 200 ? article.content.substring(0, 200).concat('...') : article.content}
                </div>
                <a href="${pageContext.request.contextPath}/article?action=view&id=${article.id}" class="btn btn-primary">Read More</a>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <div style="text-align: center; padding: 2rem; background: #f9f9f9; border-radius: 4px;">
            <p>No articles found. <a href="${pageContext.request.contextPath}/article?action=create">Create one now!</a></p>
        </div>
    </c:otherwise>
</c:choose>

