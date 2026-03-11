package com.example.blog;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class SearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("q");
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/blogdb", "user", "password");
            PreparedStatement stmt = conn.prepareStatement("SELECT id, title FROM articles WHERE title ILIKE ? OR content ILIKE ?");
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();
            List<Map<String, Object>> results = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> result = new HashMap<>();
                result.put("id", rs.getInt("id"));
                result.put("title", rs.getString("title"));
                results.add(result);
            }
            request.setAttribute("results", results);
            request.setAttribute("query", query);
            conn.close();
            request.getRequestDispatcher("search.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
