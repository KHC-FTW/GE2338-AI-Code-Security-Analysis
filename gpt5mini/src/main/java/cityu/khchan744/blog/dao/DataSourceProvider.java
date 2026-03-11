package cityu.khchan744.blog.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceProvider {
    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream in = DataSourceProvider.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties props = new Properties();
            if (in != null) props.load(in);
            url = props.getProperty("db.url", "jdbc:postgresql://localhost:5432/blogdb");
            user = props.getProperty("db.user", "postgres");
            password = props.getProperty("db.password", "postgres");
            // Optionally load driver class
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                // driver not on classpath at analysis time; runtime Tomcat should provide it via WAR/lib
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private DataSourceProvider() { }
}
