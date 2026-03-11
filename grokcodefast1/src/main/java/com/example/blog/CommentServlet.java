package com.example.blog;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        String content = request.getParameter("content");
        int userId = (Integer) request.getSession().getAttribute("userId");

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/blogdb", "user", "password");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO comments (article_id, user_id, content) VALUES (?, ?, ?)");
            stmt.setInt(1, articleId);
            stmt.setInt(2, userId);
            stmt.setString(3, content);
            stmt.executeUpdate();
            conn.close();
            response.sendRedirect("article?id=" + articleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
