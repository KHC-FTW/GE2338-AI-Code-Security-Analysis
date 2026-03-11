package cityu.khchan744.gemini3flash.servlet;

import cityu.khchan744.gemini3flash.dao.ArticleDAO;
import cityu.khchan744.gemini3flash.model.Article;
import cityu.khchan744.gemini3flash.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/create-article")
public class CreateArticleServlet extends HttpServlet {
    private final ArticleDAO articleDAO = new ArticleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/jsp/create_article.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login");
            return;
        }

        String title = req.getParameter("title");
        String content = req.getParameter("content");

        Article article = new Article();
        article.setAuthorId(user.getId());
        article.setTitle(title);
        article.setContent(content);

        if (articleDAO.createArticle(article)) {
            resp.sendRedirect("home");
        } else {
            req.setAttribute("error", "Failed to create article.");
            req.getRequestDispatcher("/WEB-INF/jsp/create_article.jsp").forward(req, resp);
        }
    }
}

