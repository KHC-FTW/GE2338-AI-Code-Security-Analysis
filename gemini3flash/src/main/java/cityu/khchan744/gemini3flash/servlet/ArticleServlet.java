package cityu.khchan744.gemini3flash.servlet;

import cityu.khchan744.gemini3flash.dao.ArticleDAO;
import cityu.khchan744.gemini3flash.dao.CommentDAO;
import cityu.khchan744.gemini3flash.model.Article;
import cityu.khchan744.gemini3flash.model.Comment;
import cityu.khchan744.gemini3flash.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/article")
public class ArticleServlet extends HttpServlet {
    private final ArticleDAO articleDAO = new ArticleDAO();
    private final CommentDAO commentDAO = new CommentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendRedirect("home");
            return;
        }

        int id = Integer.parseInt(idStr);
        Article article = articleDAO.getArticleById(id);
        if (article == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        List<Comment> comments = commentDAO.getCommentsByArticle(id);
        req.setAttribute("article", article);
        req.setAttribute("comments", comments);
        req.getRequestDispatcher("/WEB-INF/jsp/article.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int articleId = Integer.parseInt(req.getParameter("articleId"));
        String content = req.getParameter("content");

        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setUserId(user.getId());
        comment.setContent(content);

        commentDAO.addComment(comment);
        resp.sendRedirect("article?id=" + articleId);
    }
}

