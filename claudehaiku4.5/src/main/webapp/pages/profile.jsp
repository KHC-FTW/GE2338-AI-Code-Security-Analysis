<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    pageContext.setAttribute("pageTitle", "Profile");
%>
<jsp:include page="header.jsp" />

<c:choose>
    <c:when test="${not empty user}">
        <div style="max-width: 600px; margin: 0 auto;">
            <h1>My Profile</h1>

            <form method="POST" action="${pageContext.request.contextPath}/profile">
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" value="${user.username}" disabled>
                </div>

                <div class="form-group">
                    <label>Email</label>
                    <input type="email" value="${user.email}" disabled>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="firstName">First Name</label>
                        <input type="text" id="firstName" name="firstName" value="${user.firstName != null ? user.firstName : ''}">
                    </div>

                    <div class="form-group">
                        <label for="lastName">Last Name</label>
                        <input type="text" id="lastName" name="lastName" value="${user.lastName != null ? user.lastName : ''}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="bio">Bio</label>
                    <textarea id="bio" name="bio" style="min-height: 100px;">${user.bio != null ? user.bio : ''}</textarea>
                </div>

                <button type="submit" class="btn btn-success">Update Profile</button>
                <a href="${pageContext.request.contextPath}/" class="btn">Cancel</a>
            </form>

            <hr style="margin: 2rem 0;">

            <h2>My Articles</h2>
            <%
                try {
                    java.util.List<cityu.khchan744.model.Article> userArticles =
                        new cityu.khchan744.dao.ArticleDAO().getArticlesByUser(
                            ((Integer)session.getAttribute("userId")), 10, 0);
                    request.setAttribute("userArticles", userArticles);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            %>
            <c:choose>
                <c:when test="${not empty userArticles}">
                    <c:forEach var="article" items="${userArticles}">
                        <div class="article-card">
                            <h3 class="article-title">${article.title}</h3>
                            <div class="article-meta">
                                Created <fmt:formatDate value="${article.createdAt}" pattern="MMM dd, yyyy"/>
                            </div>
                            <div class="article-excerpt">
                                ${article.content.length() > 150 ? article.content.substring(0, 150).concat('...') : article.content}
                            </div>
                            <a href="${pageContext.request.contextPath}/article?action=view&id=${article.id}" class="btn">View</a>
                            <a href="${pageContext.request.contextPath}/article?action=edit&id=${article.id}" class="btn">Edit</a>
                            <form action="${pageContext.request.contextPath}/article" method="POST" style="display: inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${article.id}">
                                <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure?')">Delete</button>
                            </form>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>You haven't written any articles yet. <a href="${pageContext.request.contextPath}/article?action=create">Create one now!</a></p>
                </c:otherwise>
            </c:choose>
        </div>
    </c:when>
    <c:otherwise>
        <p>Please <a href="${pageContext.request.contextPath}/auth?action=login">login</a> to view your profile.</p>
    </c:otherwise>
</c:choose>

<jsp:include page="footer.jsp" />

