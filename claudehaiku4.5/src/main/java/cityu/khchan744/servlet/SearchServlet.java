package cityu.khchan744.servlet;

import cityu.khchan744.dao.ArticleDAO;
import cityu.khchan744.model.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private ArticleDAO articleDAO = new ArticleDAO();
    private static final int ARTICLES_PER_PAGE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("q");

        if (query == null || query.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/article?action=list");
            return;
        }

        try {
            int page = 1;
            if (request.getParameter("page") != null) {
                try {
                    page = Integer.parseInt(request.getParameter("page"));
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            int offset = (page - 1) * ARTICLES_PER_PAGE;
            List<Article> articles = articleDAO.searchArticles(query, ARTICLES_PER_PAGE, offset);
            int totalArticles = articleDAO.countSearchResults(query);
            int totalPages = (int) Math.ceil((double) totalArticles / ARTICLES_PER_PAGE);

            request.setAttribute("articles", articles);
            request.setAttribute("query", query);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("/pages/search-results.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

