package online.ecommerce.platform;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DBConfig.getUrl(), DBConfig.getUser(), DBConfig.getPass());
    }
}
