package com.onlineecommerce.web;

import com.onlineecommerce.service.ECommerceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private final ECommerceService service = new ECommerceService();

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String userEmail = session.getAttribute("userEmail").toString();
        Object cartObj = session.getAttribute("cart");
        if (!(cartObj instanceof Map)) {
            req.setAttribute("error", "Your cart is empty.");
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
            return;
        }
        Map<Integer,Integer> cart = (Map<Integer,Integer>) cartObj;
        if (cart.isEmpty()) {
            req.setAttribute("error", "Your cart is empty.");
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
            return;
        }

        boolean placed = service.placeOrder(userEmail, cart);
        if (placed) {
            session.removeAttribute("cart");
            req.setAttribute("message", "Order placed successfully!");
            req.getRequestDispatcher("/orders.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Failed to place order. Please try again.");
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
        }
    }
}