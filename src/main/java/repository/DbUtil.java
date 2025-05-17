package repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=SweetsDb;encrypt=false";
    private static final String USER = "anastasia";
    private static final String PASSWORD = "12345";

    private static final Logger log = LoggerFactory.getLogger(DbUtil.class);

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            log.info("SQL Server JDBC Driver завантажено успішно.");
        } catch (ClassNotFoundException e) {
            log.error("JDBC Driver не знайдено", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            log.debug("Підключення до БД успішне: {}", URL);
            return conn;
        } catch (SQLException e) {
            log.error("Не вдалося підключитися до бази даних", e);
            throw e;
        }
    }
}