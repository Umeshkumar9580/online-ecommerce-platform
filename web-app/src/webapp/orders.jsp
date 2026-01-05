<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="container">
    <div class="card">
        <h2>Order Status</h2>
        <c:if test="${not empty message}">
            <div class="message">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <p>Thank you — if DB is configured, your order is stored. Otherwise demo mode completed.</p>
        <p><a class="btn" href="${pageContext.request.contextPath}/products">Continue Shopping</a></p>
    </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>