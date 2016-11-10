import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by tcgogogo on 16/11/8.
 */
public class MysqlConnect {
    public static Connection conn;

    public MysqlConnect() throws IOException {
        Properties prop = new Properties();
        FileInputStream fileIn = new FileInputStream("config.properties");
        prop.load(fileIn);
        String driver = prop.getProperty("driver");
        String myurl = prop.getProperty("myurl");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(myurl, user, password);
        } catch (SQLException e) {
            System.out.println(e + "MySQL操作错误");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Connection getConnet() throws IOException {
        MysqlConnect mysqlConnect = new MysqlConnect();
        return mysqlConnect.conn;
    }
}
