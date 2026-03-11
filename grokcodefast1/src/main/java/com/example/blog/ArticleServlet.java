package com.example.blog;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class ArticleServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/blogdb", "user", "password");
            PreparedStatement stmt = conn.prepareStatement("SELECT a.title, a.content, a.created_at, u.username FROM articles a JOIN users u ON a.author_id = u.id WHERE a.id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                request.setAttribute("title", rs.getString("title"));
                request.setAttribute("content", rs.getString("content"));
                request.setAttribute("created_at", rs.getTimestamp("created_at"));
                request.setAttribute("author", rs.getString("username"));
            }
            // Get comments
            PreparedStatement stmt2 = conn.prepareStatement("SELECT c.content, c.created_at, u.username FROM comments c JOIN users u ON c.user_id = u.id WHERE c.article_id = ? ORDER BY c.created_at");
            stmt2.setInt(1, id);
            ResultSet rs2 = stmt2.executeQuery();
            List<Map<String, Object>> comments = new ArrayList<>();
            while (rs2.next()) {
                Map<String, Object> comment = new HashMap<>();
                comment.put("username", rs2.getString("username"));
                comment.put("created_at", rs2.getTimestamp("created_at"));
                comment.put("content", rs2.getString("content"));
                comments.add(comment);
            }
            request.setAttribute("comments", comments);
            request.setAttribute("articleId", id);
            conn.close();
            request.getRequestDispatcher("article.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
