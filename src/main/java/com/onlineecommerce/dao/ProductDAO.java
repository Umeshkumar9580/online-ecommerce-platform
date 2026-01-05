package com.onlineecommerce.dao;

import com.onlineecommerce.model.Product;
import com.onlineecommerce.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> listAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT id, name, description, price FROM products";
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Product p = new Product(rs.getInt("id"), rs.getString("name"),
                        rs.getString("description"), rs.getDouble("price"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // if DB empty / missing, return demo list
        if (list.isEmpty()) {
            list.add(new Product(1, "Demo Product A", "Demo description A", 19.99));
            list.add(new Product(2, "Demo Product B", "Demo description B", 29.99));
        }
        return list;
    }

    public Product findById(int id) {
        String sql = "SELECT id, name, description, price FROM products WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(rs.getInt("id"), rs.getString("name"),
                            rs.getString("description"), rs.getDouble("price"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // fallback demo
        if (id == 1) return new Product(1, "Demo Product A", "Demo description A", 19.99);
        if (id == 2) return new Product(2, "Demo Product B", "Demo description B", 29.99);
        return null;
    }
}