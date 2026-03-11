package cityu.khchan744.blog.web;

import cityu.khchan744.blog.dao.PostDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    private final PostDao postDao = new PostDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        if (q == null || q.isBlank()) {
            req.setAttribute("posts", postDao.listPublished(20,0));
        } else {
            req.setAttribute("posts", postDao.search(q, 50));
        }
        req.getRequestDispatcher("/WEB-INF/views/search.jsp").forward(req, resp);
    }
}

