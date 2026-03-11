package cityu.khchan744.servlet;

import cityu.khchan744.dao.ArticleDAO;
import cityu.khchan744.model.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/article")
public class ArticleServlet extends HttpServlet {
    private ArticleDAO articleDAO = new ArticleDAO();
    private static final int ARTICLES_PER_PAGE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("view".equals(action)) {
                viewArticle(request, response);
            } else if ("edit".equals(action)) {
                editArticle(request, response);
            } else if ("create".equals(action)) {
                createArticle(request, response);
            } else if ("list".equals(action)) {
                listArticles(request, response);
            } else {
                listArticles(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }
    }

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
            if ("save".equals(action)) {
                saveArticle(request, response, userId);
            } else if ("update".equals(action)) {
                updateArticle(request, response, userId);
            } else if ("delete".equals(action)) {
                deleteArticle(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }
    }

    private void viewArticle(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/article?action=list");
            return;
        }

        int articleId = Integer.parseInt(idParam);
        Article article = articleDAO.getArticleById(articleId);

        if (article != null) {
            request.setAttribute("article", article);
            request.getRequestDispatcher("/pages/view-article.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Article not found");
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }
    }

    private void editArticle(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/article?action=list");
            return;
        }

        int articleId = Integer.parseInt(idParam);
        Article article = articleDAO.getArticleById(articleId);

        if (article != null && article.getUserId() == userId) {
            request.setAttribute("article", article);
            request.getRequestDispatcher("/pages/edit-article.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Article not found or you don't have permission to edit it");
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }
    }

    private void createArticle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
            return;
        }

        request.getRequestDispatcher("/pages/create-article.jsp").forward(request, response);
    }

    private void listArticles(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int page = 1;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int offset = (page - 1) * ARTICLES_PER_PAGE;
        List<Article> articles = articleDAO.getAllArticles(ARTICLES_PER_PAGE, offset);
        int totalArticles = articleDAO.countAllArticles();
        int totalPages = (int) Math.ceil((double) totalArticles / ARTICLES_PER_PAGE);

        request.setAttribute("articles", articles);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/pages/articles.jsp").forward(request, response);
    }

    private void saveArticle(HttpServletRequest request, HttpServletResponse response, int userId)
            throws SQLException, ServletException, IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        if (title == null || title.isEmpty() || content == null || content.isEmpty()) {
            request.setAttribute("error", "Title and content are required");
            request.getRequestDispatcher("/pages/create-article.jsp").forward(request, response);
            return;
        }

        Article article = new Article(userId, title, content);
        int articleId = articleDAO.insertArticle(article);

        if (articleId > 0) {
            response.sendRedirect(request.getContextPath() + "/article?action=view&id=" + articleId);
        } else {
            request.setAttribute("error", "Failed to create article");
            request.getRequestDispatcher("/pages/create-article.jsp").forward(request, response);
        }
    }

    private void updateArticle(HttpServletRequest request, HttpServletResponse response, int userId)
            throws SQLException, ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("error", "Article ID is required");
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
            return;
        }

        int articleId = Integer.parseInt(idParam);
        Article article = articleDAO.getArticleById(articleId);

        if (article == null || article.getUserId() != userId) {
            request.setAttribute("error", "Article not found or you don't have permission to edit it");
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        if (title == null || title.isEmpty() || content == null || content.isEmpty()) {
            request.setAttribute("error", "Title and content are required");
            request.setAttribute("article", article);
            request.getRequestDispatcher("/pages/edit-article.jsp").forward(request, response);
            return;
        }

        article.setTitle(title);
        article.setContent(content);

        if (articleDAO.updateArticle(article)) {
            response.sendRedirect(request.getContextPath() + "/article?action=view&id=" + articleId);
        } else {
            request.setAttribute("error", "Failed to update article");
            request.setAttribute("article", article);
            request.getRequestDispatcher("/pages/edit-article.jsp").forward(request, response);
        }
    }

    private void deleteArticle(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("error", "Article ID is required");
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
            return;
        }

        int articleId = Integer.parseInt(idParam);
        Article article = articleDAO.getArticleById(articleId);

        if (article == null || article.getUserId() != userId) {
            request.setAttribute("error", "Article not found or you don't have permission to delete it");
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
            return;
        }

        if (articleDAO.deleteArticle(articleId)) {
            response.sendRedirect(request.getContextPath() + "/article?action=list");
        } else {
            request.setAttribute("error", "Failed to delete article");
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }
    }
}

