package dataBaseUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import utils.Messages;

public class DatabaseUtil {
    private static final String URL = "jdbc:hsqldb:file:./data/pointsdb;shutdown=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(Messages.get("error.db.driver"), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
