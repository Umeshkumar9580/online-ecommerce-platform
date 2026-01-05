<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.onlineecommerce.service.ECommerceService" %>
<%@ page import="com.onlineecommerce.model.Product" %>
<%
    ECommerceService service = new ECommerceService();
    Map<Integer,Integer> cart = (Map<Integer,Integer>) session.getAttribute("cart");
    if (cart == null) cart = java.util.Collections.emptyMap();
%>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="container">
    <h2 style="color:white">Your Cart</h2>

    <c:if test="${empty cart}">
        <div class="card">
            <p>Your cart is empty. <a href="${pageContext.request.contextPath}/products">Continue shopping</a></p>
        </div>
    </c:if>

    <c:if test="${not empty cart}">
        <div class="card">
            <table class="cart-table">
                <thead>
                <tr><th>Product</th><th>Price</th><th>Qty</th><th>Subtotal</th><th>Action</th></tr>
                </thead>
                <tbody>
                <%
                    double total = 0;
                    for (Map.Entry<Integer,Integer> e : cart.entrySet()) {
                        Integer pid = e.getKey();
                        Integer qty = e.getValue();
                        Product product = service.getProductById(pid);
                        if (product == null) continue;
                        double subtotal = product.getPrice() * qty;
                        total += subtotal;
                %>
                <tr>
                    <td><%= product.getName() %></td>
                    <td>$<%= String.format("%.2f", product.getPrice()) %></td>
                    <td><%= qty %></td>
                    <td>$<%= String.format("%.2f", subtotal) %></td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/cart" style="display:inline">
                            <input type="hidden" name="action" value="remove" />
                            <input type="hidden" name="id" value="<%= pid %>" />
                            <button type="submit" class="btn danger">Remove</button>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>

            <div class="cart-total">
                <div style="margin-right:16px"><strong>Total: $<%= String.format("%.2f", total) %></strong></div>
                <form method="post" action="${pageContext.request.contextPath}/order">
                    <button type="submit" class="btn primary">Place Order</button>
                </form>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
    <c:if test="${not empty message}"><div class="message">${message}</div></c:if>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>