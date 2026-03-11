package cityu.khchan744.blog.web;

import cityu.khchan744.blog.model.User;
import cityu.khchan744.blog.service.AuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String display = req.getParameter("displayName");

        if (username == null || username.isBlank() || email == null || email.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("error", "Please fill required fields");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        User u = authService.register(username, email, password, display);
        HttpSession session = req.getSession();
        session.setAttribute("userId", u.getId());
        session.setAttribute("username", u.getUsername());
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
