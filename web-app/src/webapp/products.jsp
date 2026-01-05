<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="container">
    <h2 style="color:white;margin-bottom:12px">Products</h2>
    <div class="grid">
        <c:forEach var="p" items="${products}">
            <div class="card product-card">
                <div class="image-placeholder">IMG</div>
                <h3><c:out value="${p.name}"/></h3>
                <p class="price">$<c:out value="${p.price}"/></p>
                <p class="desc"><c:out value="${p.description}"/></p>
                <form method="post" action="${pageContext.request.contextPath}/cart">
                    <input type="hidden" name="action" value="add"/>
                    <input type="hidden" name="id" value="${p.id}"/>
                    <input type="number" name="qty" value="1" min="1" class="qty" />
                    <button class="btn primary" type="submit">Add to cart</button>
                </form>
            </div>
        </c:forEach>
    </div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>