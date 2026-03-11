<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    pageContext.setAttribute("pageTitle", "Error");
%>
<jsp:include page="header.jsp" />

<div style="text-align: center; padding: 3rem 2rem; max-width: 600px; margin: 0 auto;">
    <h1 style="color: #dc3545; font-size: 3rem;">⚠️ Error</h1>
    <p style="font-size: 1.2rem; margin-bottom: 1rem;">${error != null ? error : 'An error occurred'}</p>

    <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Back to Home</a>
    <a href="javascript:history.back()" class="btn">Go Back</a>
</div>

<jsp:include page="footer.jsp" />

