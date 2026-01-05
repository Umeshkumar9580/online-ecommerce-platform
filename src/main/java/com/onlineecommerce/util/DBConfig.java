package online.ecommerce.platform;

import java.io.*;
import java.util.Properties;

/**
 * Reads DB configuration from db.properties if present.
 * If not present, uses default values. For submission, change db.properties instead of code.
 */
public class DBConfig {
    private static final String PROPS_FILE = "db.properties";
    private static final Properties props = new Properties();

    static {
        // defaults
        props.setProperty("db.url", "jdbc:mysql://localhost:3306/ecommerce_review1?serverTimezone=UTC");
        props.setProperty("db.user", "root");
        props.setProperty("db.pass", "@Umesh0123");

        // load from file if available
        try (InputStream in = new FileInputStream(PROPS_FILE)) {
            props.load(in);
        } catch (IOException e) {
            // ignore: use defaults
        }
    }

    public static String getUrl() { return props.getProperty("db.url"); }
    public static String getUser() { return props.getProperty("db.user"); }
    public static String getPass() { return props.getProperty("db.pass"); }
}
