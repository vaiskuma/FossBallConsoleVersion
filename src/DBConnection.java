/**
 * Created by Kompas on 2017-04-10.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private final static String URL = "jdbc:mysql://54.70.10.89:3306/";//54.70.10.89/phpmyadmin is what you type in the URL if you want to connect to the database
    private final static String DB_NAME = "foosball";
    private final static String USER = "hans";
    private final static String PASS = "minecraft64";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    URL + DB_NAME,
                    USER,
                    PASS);
            return con;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

