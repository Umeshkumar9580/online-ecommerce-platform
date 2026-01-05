<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<section class="hero-modern">
    <div class="hero-overlay"></div>

    <div class="hero-content">
        <span class="badge">⚡ SMART E-COMMERCE</span>

        <h1>
            Buy & Sell <br>
            <span>Electronics Seamlessly</span>
        </h1>

        <p>
            Laptops • Mobiles • Accessories <br>
            Secure login, fast checkout and smooth experience.
        </p>

        <div class="hero-actions">
            <a href="${pageContext.request.contextPath}/login" class="btn-primary">
                Get Started
            </a>
            <a href="${pageContext.request.contextPath}/products" class="btn-outline">
                Explore Products
            </a>
        </div>
    </div>
</section>

<%@ include file="/WEB-INF/includes/footer.jsp" %>
