<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile - MyBlog</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f4f4; margin: 0; }
        .profile-box { max-width: 600px; margin: 2rem auto; padding: 2rem; background: white; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .form-group { margin-bottom: 1rem; }
        .form-group label { display: block; margin-bottom: 0.5rem; }
        .form-group input, .form-group textarea { width: 100%; box-sizing: border-box; padding: 0.5rem; }
        .btn { padding: 0.5rem 1rem; background: #333; color: white; border: none; cursor: pointer; }
        .success { color: green; margin-bottom: 1rem; }
    </style>
</head>
<body>
    <%@ include file="common/header.jspf" %>
    <div class="profile-box">
        <h2>My Profile</h2>
        <c:if test="${param.success == '1'}">
            <div class="success">Profile updated successfully.</div>
        </c:if>
        <form action="profile" method="post">
            <div class="form-group">
                <label>Username</label>
                <input type="text" value="${profile.username}" disabled>
            </div>
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="fullName" value="${profile.fullName}" required>
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" value="${profile.email}" required>
            </div>
            <div class="form-group">
                <label>Bio</label>
                <textarea name="bio" rows="5">${profile.bio}</textarea>
            </div>
            <button type="submit" class="btn">Update Profile</button>
        </form>
    </div>
</body>
</html>

