package com.onlineecommerce.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }
}
