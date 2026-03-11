package cityu.khchan744.gemini3flash.servlet;

import cityu.khchan744.gemini3flash.dao.UserDAO;
import cityu.khchan744.gemini3flash.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login");
            return;
        }

        User updatedUser = userDAO.getUserById(user.getId());
        req.setAttribute("profile", updatedUser);
        req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect("login");
            return;
        }

        String email = req.getParameter("email");
        String fullName = req.getParameter("fullName");
        String bio = req.getParameter("bio");

        User u = new User();
        u.setId(currentUser.getId());
        u.setEmail(email);
        u.setFullName(fullName);
        u.setBio(bio);

        if (userDAO.updateUser(u)) {
            // Update session user
            User updated = userDAO.getUserById(u.getId());
            req.getSession().setAttribute("user", updated);
            resp.sendRedirect("profile?success=1");
        } else {
            req.setAttribute("error", "Update failed.");
            req.setAttribute("profile", u);
            req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
        }
    }
}

