package cityu.khchan744.blog.web;

import cityu.khchan744.blog.dao.UserDao;
import cityu.khchan744.blog.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int uid = (int) s.getAttribute("userId");
        Optional<User> u = userDao.findById(uid);
        if (u.isEmpty()) { resp.sendError(HttpServletResponse.SC_NOT_FOUND); return; }
        req.setAttribute("user", u.get());
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("userId") == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        int uid = (int) s.getAttribute("userId");
        Optional<User> uo = userDao.findById(uid);
        if (uo.isEmpty()) { resp.sendError(HttpServletResponse.SC_NOT_FOUND); return; }
        User u = uo.get();
        String email = req.getParameter("email");
        String display = req.getParameter("displayName");
        u.setEmail(email);
        u.setDisplayName(display);
        userDao.update(u);
        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}

