package online.ecommerce.platform;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO: CRUD methods for Product.
 * Uses try-with-resources to properly close resources.
 */
public class ProductDAO {

    public void insertProduct(Product p) throws Exception {
        String sql = "INSERT INTO products (id, name, description, price, quantity, link, is_digital) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getDescription());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getQuantity());

            if (p instanceof DigitalProduct) {
                ps.setString(6, ((DigitalProduct) p).getLink());
                ps.setBoolean(7, true);
            } else {
                ps.setNull(6, Types.VARCHAR);
                ps.setBoolean(7, false);
            }

            ps.executeUpdate();
        }
    }

    public void updateProduct(Product p) throws Exception {
        String sql = "UPDATE products SET name=?, description=?, price=?, quantity=?, link=?, is_digital=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getQuantity());

            if (p instanceof DigitalProduct) {
                ps.setString(5, ((DigitalProduct) p).getLink());
                ps.setBoolean(6, true);
            } else {
                ps.setNull(5, Types.VARCHAR);
                ps.setBoolean(6, false);
            }
            ps.setInt(7, p.getId());
            ps.executeUpdate();
        }
    }

    public void deleteProduct(int id) throws Exception {
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Product getProductById(int id) throws Exception {
        String sql = "SELECT * FROM products WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToProduct(rs);
                }
            }
        }
        return null;
    }

    public List<Product> getAllProducts() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRowToProduct(rs));
            }
        }
        return list;
    }

    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String desc = rs.getString("description");
        double price = rs.getDouble("price");
        int qty = rs.getInt("quantity");
        String link = rs.getString("link");
        boolean isDigital = rs.getBoolean("is_digital");

        if (isDigital) {
            return new DigitalProduct(id, name, desc, price, qty, link);
        } else {
            return new Product(id, name, desc, price, qty);
        }
    }
}
