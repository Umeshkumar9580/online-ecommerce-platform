<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Login - My Shop</title>

    <!-- core site styles (kept for other pages) -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css" />
    <!-- login-specific styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css" />
</head>
<body class="login-page">

<div class="login-wrap">
    <div class="login-card" role="main" aria-labelledby="loginTitle">
        <h2 id="loginTitle">Login</h2>

        <form method="post" action="${pageContext.request.contextPath}/login" data-validate="login" novalidate>
            <div class="form-row">
                <label for="email">Email</label>
                <div class="input-with-icon">
                    <input id="email" name="email" type="email" placeholder="you@example.com" value="${param.email != null ? param.email : ''}" required />
                    <div class="input-icon">
                        <!-- simple email icon SVG -->
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">
                            <path d="M3 6.5L12 13L21 6.5" stroke="white" stroke-opacity="0.9" stroke-width="1.2" stroke-linecap="round" stroke-linejoin="round"/>
                            <rect x="3" y="4" width="18" height="14" rx="2" stroke="white" stroke-opacity="0.06" fill="none"/>
                        </svg>
                    </div>
                </div>
            </div>

            <div class="form-row">
                <label for="password">Password</label>
                <div class="input-with-icon">
                    <input id="password" name="password" type="password" placeholder="At least 6 characters" required />
                    <div class="input-icon">
                        <!-- lock svg -->
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">
                            <rect x="5" y="11" width="14" height="9" rx="2" stroke="white" stroke-opacity="0.9" stroke-width="1.2"/>
                            <path d="M8 11V8a4 4 0 1 1 8 0v3" stroke="white" stroke-opacity="0.9" stroke-width="1.2" stroke-linecap="round"/>
                        </svg>
                    </div>
                </div>
            </div>

            <div class="misc-row">
                <div class="remember">
                    <input id="remember" name="remember" type="checkbox" />
                    <label for="remember" style="font-size:0.9rem;color:rgba(255,255,255,0.85)">Remember me</label>
                </div>
                <div class="forgot">
                    <a href="#" style="color:rgba(255,255,255,0.85);text-decoration:underline">Forgot password?</a>
                </div>
            </div>

            <div class="form-row">
                <button type="submit" class="btn-login">Login</button>
            </div>

            <div class="register-row">
                Don't have an account? <a href="${pageContext.request.contextPath}/register">Register</a>
            </div>

            <div>
                <c:if test="${not empty error}">
                    <div class="inline-error">${error}</div>
                </c:if>
                <c:if test="${not empty message}">
                    <div class="message" style="color:#b7f5d9;margin-top:8px">${message}</div>
                </c:if>
            </div>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/validation.js"></script>
</body>
</html>