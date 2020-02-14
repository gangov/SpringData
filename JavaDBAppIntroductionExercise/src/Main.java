import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        String user = "root";
        String password = "admin123QWE";
        String host = "jdbc:mysql://localhost:3306/minions_db";

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection(host, properties);

        MainEngine engine = new MainEngine(connection);

        engine.run();
    }
}
