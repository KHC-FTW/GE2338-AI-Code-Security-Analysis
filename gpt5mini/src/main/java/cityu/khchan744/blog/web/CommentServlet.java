package cityu.khchan744.blog.web;

import cityu.khchan744.blog.dao.CommentDao;
import cityu.khchan744.blog.model.Comment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CommentServlet", urlPatterns = {"/comment"})
public class CommentServlet extends HttpServlet {
    private final CommentDao commentDao = new CommentDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postId = req.getParameter("postId");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String content = req.getParameter("content");
        if (postId == null || content == null || content.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Comment cm = new Comment();
        cm.setPostId(Integer.parseInt(postId));
        cm.setAuthorName(name == null ? "Anonymous" : name);
        cm.setAuthorEmail(email);
        cm.setContent(content);
        cm.setApproved(true);
        commentDao.create(cm);
        resp.sendRedirect(req.getContextPath() + "/post?slug=" + req.getParameter("slug"));
    }
}

