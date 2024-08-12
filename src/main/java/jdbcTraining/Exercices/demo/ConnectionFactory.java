package jdbcTraining.Exercices.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/training_jdbc_bd";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Dg3HIJ50__";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}