package com.onlineecommerce.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                props.load(in);
                Class.forName(props.getProperty("db.driver", "com.mysql.cj.jdbc.Driver"));
            } else {
                System.err.println("db.properties not found on classpath; DB operations will fail unless using demo mode.");
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws Exception {
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");
        if (url == null) throw new IllegalStateException("db.url not configured in db.properties");
        return DriverManager.getConnection(url, user, pass);
    }
}