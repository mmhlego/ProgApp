package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connector {
    private static Connection connection;

    public static boolean Connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:resources/database/Database.db");
            return connection != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet RunCommand(String Command) {
        try {
            return connection.createStatement().executeQuery(Command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection() {
        return connection;
    }
}
