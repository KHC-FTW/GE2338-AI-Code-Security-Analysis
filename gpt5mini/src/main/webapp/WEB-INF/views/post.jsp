<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp" %>
<%-- simple post view --%>
<c:if test="${empty post}">
    <p>Post not found.</p>
</c:if>
<c:if test="${not empty post}">
    <h1>${post.title}</h1>
    <div>${post.content}</div>

    <h3>Comments</h3>
    <c:if test="${not empty post}">
        <c:set var="comments" value="${requestScope['comments']}" />
        <c:if test="${not empty comments}">
            <ul>
                <c:forEach var="c" items="${comments}">
                    <li><strong>${c.authorName}</strong>: ${c.content}</li>
                </c:forEach>
            </ul>
        </c:if>
    </c:if>

    <h4>Leave a comment</h4>
    <form method="post" action="${pageContext.request.contextPath}/comment">
        <input type="hidden" name="postId" value="${post.id}" />
        <input type="hidden" name="slug" value="${post.slug}" />
        <label>Name: <input type="text" name="name" /></label><br/>
        <label>Email: <input type="email" name="email" /></label><br/>
        <label>Comment:<br/><textarea name="content" rows="4" cols="60"></textarea></label><br/>
        <button type="submit">Post Comment</button>
    </form>
</c:if>
<%@ include file="includes/footer.jsp" %>
