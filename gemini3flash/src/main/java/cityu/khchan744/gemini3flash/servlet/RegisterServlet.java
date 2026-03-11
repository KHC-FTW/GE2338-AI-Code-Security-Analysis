package cityu.khchan744.gemini3flash.servlet;

import cityu.khchan744.gemini3flash.dao.UserDAO;
import cityu.khchan744.gemini3flash.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        String email = req.getParameter("email");
        String fullName = req.getParameter("fullName");

        User u = new User();
        u.setUsername(user);
        u.setPassword(pass);
        u.setEmail(email);
        u.setFullName(fullName);

        if (userDAO.register(u)) {
            resp.sendRedirect("login?success=1");
        } else {
            req.setAttribute("error", "Registration failed. Username or email might be taken.");
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
        }
    }
}

