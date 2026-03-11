package cityu.khchan744.gemini3flash.servlet;

import cityu.khchan744.gemini3flash.dao.ArticleDAO;
import cityu.khchan744.gemini3flash.model.Article;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home", ""})
public class HomeServlet extends HttpServlet {
    private final ArticleDAO articleDAO = new ArticleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("q");
        List<Article> articles;
        if (query != null && !query.trim().isEmpty()) {
            articles = articleDAO.searchArticles(query);
            req.setAttribute("searchQuery", query);
        } else {
            articles = articleDAO.getAllArticles();
        }
        req.setAttribute("articles", articles);
        req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
    }
}

