package cityu.khchan744.blog.web;

import cityu.khchan744.blog.dao.CommentDao;
import cityu.khchan744.blog.dao.PostDao;
import cityu.khchan744.blog.model.Post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "PostViewServlet", urlPatterns = {"/post"})
public class PostViewServlet extends HttpServlet {
    private final PostDao postDao = new PostDao();
    private final CommentDao commentDao = new CommentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String slug = req.getParameter("slug");
        if (slug == null || slug.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Optional<Post> postOpt = postDao.findBySlug(slug);
        if (postOpt.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Post post = postOpt.get();
        req.setAttribute("post", post);
        req.setAttribute("comments", commentDao.findByPostId(post.getId()));
        req.getRequestDispatcher("/WEB-INF/views/post.jsp").forward(req, resp);
    }
}
