package com.onlineecommerce.web;

import com.onlineecommerce.service.ECommerceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final ECommerceService service = new ECommerceService();
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email") != null ? req.getParameter("email").trim() : "";
        String password = req.getParameter("password") != null ? req.getParameter("password") : "";

        // Server-side validation
        if (!EMAIL_REGEX.matcher(email).matches()) {
            req.setAttribute("error", "Please enter a valid email address.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }
        if (password.length() < 6) {
            req.setAttribute("error", "Password must be at least 6 characters.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        boolean ok = service.authenticate(email, password);
        if (ok) {
            HttpSession s = req.getSession(true);
            s.setAttribute("userEmail", email);
            // Optionally set session timeout
            s.setMaxInactiveInterval(30 * 60); // 30 minutes
            resp.sendRedirect(req.getContextPath() + "/products");
        } else {
            req.setAttribute("error", "Invalid email or password.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}