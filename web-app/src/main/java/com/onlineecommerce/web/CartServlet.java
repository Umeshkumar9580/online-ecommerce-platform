package com.onlineecommerce.web;

import com.onlineecommerce.service.ECommerceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final ECommerceService service = new ECommerceService();

    @SuppressWarnings("unchecked")
    private Map<Integer,Integer> getCart(HttpSession session) {
        Object o = session.getAttribute("cart");
        if (o instanceof Map) return (Map<Integer,Integer>) o;
        Map<Integer,Integer> cart = new HashMap<>();
        session.setAttribute("cart", cart);
        return cart;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        String action = req.getParameter("action");
        String idParam = req.getParameter("id");
        int productId = idParam != null ? Integer.parseInt(idParam) : -1;
        Map<Integer,Integer> cart = getCart(session);

        if ("add".equals(action) && productId > 0) {
            int qty = 1;
            try {
                String q = req.getParameter("qty");
                if (q != null) qty = Math.max(1, Integer.parseInt(q));
            } catch (Exception ignored) {}
            cart.put(productId, cart.getOrDefault(productId, 0) + qty);
        } else if ("remove".equals(action) && productId > 0) {
            cart.remove(productId);
        } else if ("update".equals(action) && productId > 0) {
            try {
                int qty = Integer.parseInt(req.getParameter("qty"));
                if (qty > 0) cart.put(productId, qty); else cart.remove(productId);
            } catch (Exception ignored) {}
        }

        session.setAttribute("cart", cart);
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}