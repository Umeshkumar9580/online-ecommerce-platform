# 🛒 Online E-Commerce Platform (Java Web Application)

A full-stack **E-Commerce Web Application** built using **Java (Servlets & JSP)** following the **MVC architecture**.  
The project simulates a real-world online shopping platform with authentication, product browsing, cart management, and session handling.

This project is developed for **academic (college) purposes** to demonstrate Java Web technologies and clean project structure.

---

## 🚀 Features

### ✅ Web-Based Application (Servlet + JSP)
- Dynamic and responsive user interface
- Clean navigation bar with reusable header & footer
- Modern UI inspired by real e-commerce websites
- Centralized styling using CSS

---

### ✅ User Authentication & Validation
- Secure login system using Servlets
- Server-side **email format validation**
- Password length validation
- Proper error messages for invalid inputs
- Session-based authentication handling

---

### ✅ Product Management
- Display of multiple products (e.g. laptops, electronics)
- Product name, price, description & image placeholders
- Dynamic rendering using **JSP + JSTL**
- Grid-based product layout similar to Amazon-style UI

---

### ✅ Shopping Cart Functionality
- Add products to cart
- Quantity selection
- Session-based cart storage
- Cart summary with total calculation

---

### ✅ Guest Mode Support
- Users can browse products without login
- Login required only for checkout
- Smooth and user-friendly navigation flow

---

## 🧠 Core Concepts Used

### 🔹 Java Web Technologies
- Servlets & Servlet lifecycle
- JSP & JSTL tags
- HTTP Request/Response handling
- URL mapping using `@WebServlet`
- Session management (`HttpSession`)

---

### 🔹 OOP Principles
- Encapsulation (private fields with getters/setters)
- Abstraction using service layer
- Modular and reusable components
- Clean separation of concerns (MVC)

---

## 🧰 Technology Stack

- **Java (JDK 17)**
- **Servlet API**
- **JSP & JSTL**
- **HTML5**
- **CSS3**
- **Apache Tomcat 9**
- **Maven**
- **IntelliJ IDEA**

---

## 📁 Project Folder Structure
```
online-ecommerce-platform/
│
├── web-app/
│ ├── src/
│ │ └── main/
│ │ ├── java/
│ │ │ └── com.onlineecommerce.web/
│ │ │ ├── HomeServlet.java
│ │ │ ├── LoginServlet.java
│ │ │ ├── ProductServlet.java
│ │ │ ├── CartServlet.java
│ │ │ └── LogoutServlet.java
│ │ │
│ │ └── webapp/
│ │ ├── assets/
│ │ │ ├── css/
│ │ │ │ ├── theme.css
│ │ │ │ ├── styles.css
│ │ │ │ ├── auth.css
│ │ │ │ └── products.css
│ │ │ ├── images/
│ │ │ └── js/
│ │ │
│ │ ├── landing.jsp
│ │ ├── login.jsp
│ │ ├── products.jsp
│ │ ├── cart.jsp
│ │ ├── error.jsp
│ │ │
│ │ └── WEB-INF/
│ │ ├── web.xml
│ │ └── includes/
│ │ ├── header.jsp
│ │ └── footer.jsp
│
├── pom.xml
└── README.md
```

---

## ▶️ How to Run the Project

### ✅ Prerequisites
- JDK 17 installed
- Apache Tomcat 9
- IntelliJ IDEA
- Maven configured

---

### ▶️ Steps to Execute
1. Open the project in **IntelliJ IDEA**
2. Configure **Apache Tomcat Server**
3. Deploy `web-app : war exploded`
4. Start the server
5. Open browser and visit:


---

## 🧪 Validation & Error Handling

- Email format validation
- Password length validation
- User-friendly error messages
- Safe request forwarding
- Centralized error page handling

---

## 🎨 UI & User Experience

- Responsive layout
- Clean typography
- Center-aligned landing section
- Consistent color theme
- Professional UI suitable for demos and viva

---

## 🚀 Future Enhancements

- User registration module
- Database-driven product management
- Order placement system
- Payment gateway integration
- Admin dashboard

---

## 👨‍💻 Author

**Name:** Umesh Kumar  
**Course:** B.Tech – Computer Science & Engineering

---

## ✅ Conclusion

This project demonstrates:
- Java Web Application development
- MVC architecture
- Clean UI integration
- Proper validation & session handling

**Fully functional and perfect for college submission.**
