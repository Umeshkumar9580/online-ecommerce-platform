# 🛒 Online E-Commerce Platform (Java Swing GUI)

A desktop-based *E-Commerce Management System* built using *JAVA (Swing GUI)* with proper *OOP, **DAO Architecture, **Collections, **Multithreading, and **JDBC database connectivity*.

This project allows users to *Add, Update, Delete, Search, and Manage products* with both physical and digital item support.

---

## 🚀 Features

### ✔ GUI-Based Desktop Application (Swing)
- Clean and simple user interface  
- Product table with sorting & searching  
- Pop-up form dialog for adding/editing products  
- Separate window components using Swing + AWT layouts  

### ✔ Product Management
- Add new products  
- Edit existing products  
- Delete products  
- Search by name / ID  
- Supports both *PhysicalProduct* & *DigitalProduct*  
- Automatic price validation & exception handling  

### ✔ OOP Concepts Used
- *Inheritance* (Product → PhysicalProduct / DigitalProduct)  
- *Polymorphism* (Sellable interface implemented by different product types)  
- *Abstraction* (DAO layer, service layer)  
- *Encapsulation* (All fields private with getters/setters)  
- *Custom Exceptions* (InvalidProductException)

### ✔ Collections & Generics
- Manages product list using *ArrayList\<Product>*  
- Generic models used in table rendering  

### ✔ Multithreading
- Background AutoSaveThread automatically saves product cache to DB.  
- Continuous database sync without blocking UI.  

### ✔ Database (JDBC + MySQL)
- DB connection through DAO classes  
- PreparedStatements used for secure queries  
- Configurable via db.properties

---

## 📁 Project Folder Structure

online-ecommerce-platform/
│
├── src/
│   ├── model/
│   │   ├── Product.java
│   │   ├── DigitalProduct.java
│   │   ├── PhysicalProduct.java
│   │   └── Sellable.java
│   │
│   ├── dao/
│   │   ├── ProductDAO.java
│   │   └── DBConnection.java
│   │
│   ├── service/
│   │   └── ECommerceService.java
│   │
│   ├── ui/
│   │   └── ProjectGUI.java
│   │
│   ├── util/
│   │   └── AutoSaveThread.java
│   │
│   ├── db.properties
│   └── Main.java
│
├── database/
│   └── ecommerce.sql
│
└── README.md
