<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Shop</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- MAIN GLOBAL STYLES -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>

<body class="site-body">

<header class="topbar">
    <div class="topbar-inner">

        <!-- Brand -->
        <div class="brand">
            <a href="${pageContext.request.contextPath}/" class="brand-link">
                ⚡ My Shop
            </a>
        </div>

        <!-- Navigation -->
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/products" class="nav-item">
                Shop
            </a>
            <a href="${pageContext.request.contextPath}/cart" class="nav-item">
                Cart
            </a>
        </nav>

        <!-- User Area -->
        <div class="user-area">
            <c:choose>
                <c:when test="${not empty sessionScope.userEmail}">
                    <span class="user-email">
                            ${sessionScope.userEmail}
                    </span>
                    <a class="btn small"
                       href="${pageContext.request.contextPath}/logout">
                        Logout
                    </a>
                </c:when>

                <c:otherwise>
                    <a class="btn small"
                       href="${pageContext.request.contextPath}/login">
                        Login
                    </a>
                </c:otherwise>
            </c:choose>
        </div>

    </div>
</header>
