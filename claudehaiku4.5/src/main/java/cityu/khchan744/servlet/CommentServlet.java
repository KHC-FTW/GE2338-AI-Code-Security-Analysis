package cityu.khchan744.servlet;

import cityu.khchan744.dao.CommentDAO;
import cityu.khchan744.model.Comment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    private CommentDAO commentDAO = new CommentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                addComment(request, response, userId);
            } else if ("delete".equals(action)) {
                deleteComment(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }
    }

    private void addComment(HttpServletRequest request, HttpServletResponse response, int userId)
            throws SQLException, ServletException, IOException {
        String articleIdParam = request.getParameter("articleId");
        String content = request.getParameter("content");

        if (articleIdParam == null || articleIdParam.isEmpty() || content == null || content.isEmpty()) {
            request.setAttribute("error", "Article ID and comment content are required");
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
            return;
        }

        int articleId = Integer.parseInt(articleIdParam);
        Comment comment = new Comment(articleId, userId, content);

        if (commentDAO.insertComment(comment) > 0) {
            response.sendRedirect(request.getContextPath() + "/article?action=view&id=" + articleId);
        } else {
            request.setAttribute("error", "Failed to add comment");
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }
    }

    private void deleteComment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        String commentIdParam = request.getParameter("commentId");
        String articleIdParam = request.getParameter("articleId");

        if (commentIdParam == null || commentIdParam.isEmpty()) {
            request.setAttribute("error", "Comment ID is required");
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
            return;
        }

        int commentId = Integer.parseInt(commentIdParam);
        Comment comment = commentDAO.getCommentById(commentId);

        if (comment == null || comment.getUserId() != userId) {
            request.setAttribute("error", "Comment not found or you don't have permission to delete it");
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
            return;
        }

        if (commentDAO.deleteComment(commentId)) {
            if (articleIdParam != null && !articleIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/article?action=view&id=" + articleIdParam);
            } else {
                response.sendRedirect(request.getContextPath() + "/article?action=list");
            }
        } else {
            request.setAttribute("error", "Failed to delete comment");
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }
    }
}

