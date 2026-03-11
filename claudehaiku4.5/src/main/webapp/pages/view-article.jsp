<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    pageContext.setAttribute("pageTitle", "Article");
%>
<jsp:include page="header.jsp" />

<c:choose>
    <c:when test="${not empty article}">
        <div style="max-width: 800px; margin: 0 auto;">
            <article>
                <h1>${article.title}</h1>
                <div class="article-meta">
                    By <strong>${article.authorUsername}</strong> |
                    <fmt:formatDate value="${article.createdAt}" pattern="MMM dd, yyyy hh:mm a"/>
                    <c:if test="${article.createdAt != article.updatedAt}">
                        | Updated <fmt:formatDate value="${article.updatedAt}" pattern="MMM dd, yyyy hh:mm a"/>
                    </c:if>
                </div>

                <c:if test="${sessionScope.userId == article.userId}">
                    <div style="margin-bottom: 1.5rem;">
                        <a href="${pageContext.request.contextPath}/article?action=edit&id=${article.id}" class="btn">Edit</a>
                        <form action="${pageContext.request.contextPath}/article" method="POST" style="display: inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${article.id}">
                            <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure?')">Delete</button>
                        </form>
                    </div>
                </c:if>

                <hr style="margin: 1.5rem 0;">

                <div style="white-space: pre-wrap; line-height: 1.8; color: #333;">
                    ${article.content}
                </div>
            </article>

            <hr style="margin: 2rem 0;">

            <section>
                <h2>Comments</h2>

                <%
                    try {
                        int articleId = (Integer)request.getAttribute("article") != null ?
                            ((cityu.khchan744.model.Article)request.getAttribute("article")).getId() : 0;
                        if (articleId > 0) {
                            java.util.List<cityu.khchan744.model.Comment> comments =
                                new cityu.khchan744.dao.CommentDAO().getCommentsByArticle(articleId, 100, 0);
                            request.setAttribute("comments", comments);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                %>

                <c:if test="${not empty sessionScope.userId}">
                    <div style="background: #f9f9f9; padding: 1.5rem; border-radius: 4px; margin-bottom: 2rem;">
                        <h3>Leave a Comment</h3>
                        <form method="POST" action="${pageContext.request.contextPath}/comment">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="articleId" value="${article.id}">

                            <div class="form-group">
                                <label for="content">Your Comment</label>
                                <textarea id="content" name="content" required style="min-height: 100px;"></textarea>
                            </div>

                            <button type="submit" class="btn btn-success">Post Comment</button>
                        </form>
                    </div>
                </c:if>

                <c:choose>
                    <c:when test="${not empty comments}">
                        <div style="margin-top: 2rem;">
                            <c:forEach var="comment" items="${comments}">
                                <div class="comment">
                                    <div class="comment-author">${comment.authorUsername}</div>
                                    <div class="comment-date"><fmt:formatDate value="${comment.createdAt}" pattern="MMM dd, yyyy hh:mm a"/></div>
                                    <div class="comment-content">${comment.content}</div>
                                    <c:if test="${sessionScope.userId == comment.userId}">
                                        <form action="${pageContext.request.contextPath}/comment" method="POST" style="display: inline; margin-top: 0.5rem;">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="commentId" value="${comment.id}">
                                            <input type="hidden" name="articleId" value="${article.id}">
                                            <button type="submit" class="btn btn-danger" style="padding: 0.25rem 0.75rem; font-size: 0.9rem;" onclick="return confirm('Are you sure?')">Delete</button>
                                        </form>
                                    </c:if>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p style="text-align: center; color: #999;">No comments yet. Be the first to comment!</p>
                    </c:otherwise>
                </c:choose>
            </section>
        </div>
    </c:when>
    <c:otherwise>
        <div style="text-align: center; padding: 2rem;">
            <p>Article not found.</p>
            <a href="${pageContext.request.contextPath}/article?action=list" class="btn">Back to Articles</a>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="footer.jsp" />

