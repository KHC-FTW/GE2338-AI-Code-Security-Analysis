package cityu.khchan744.blog.web;

import cityu.khchan744.blog.dao.PostDao;
import cityu.khchan744.blog.model.Post;
import cityu.khchan744.blog.util.SlugUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "PostEditServlet", urlPatterns = {"/post-edit"})
public class PostEditServlet extends HttpServlet {
    private final PostDao postDao = new PostDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/post-edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int uid = (int) s.getAttribute("userId");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String summary = req.getParameter("summary");
        String publish = req.getParameter("publish");
        boolean published = "on".equalsIgnoreCase(publish) || "true".equalsIgnoreCase(publish);
        if (title == null || title.isBlank() || content == null || content.isBlank()) {
            req.setAttribute("error", "Title and content are required");
            req.getRequestDispatcher("/WEB-INF/views/post-edit.jsp").forward(req, resp);
            return;
        }
        Post p = new Post();
        p.setAuthorId(uid);
        p.setTitle(title);
        p.setSlug(SlugUtil.toSlug(title));
        p.setContent(content);
        p.setSummary(summary);
        p.setPublished(published);
        postDao.create(p);
        resp.sendRedirect(req.getContextPath() + "/post?slug=" + p.getSlug());
    }
}

