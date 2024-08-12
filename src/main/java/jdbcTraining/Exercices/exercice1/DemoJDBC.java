package jdbcTraining.Exercices.exercice1;

import BStorm.JDBC.ConnectionFactory;
import BStorm.UI.ColPrinter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DemoJDBC {

    public static void main(String[] args) {

    }

    // 1. Chiant et SQL ERROR
    // Connection connection = DriverManager.getConnection(
    //                "jdbc:postgresql://localhost:5432/jdbc_training_bd",
    //                "postgres", "Dg3HIJ50__" )


    // 2. SQL ERROR + Création fonction utilitaire
    //    private static final String SERVER_URL = "jdbc:postgresql://localhost:5432/";
    //    private static final String USERNAME = "postgres";
    //    private static final String PASSWORD = "Dg3HIJ50__";
    //
    //    public static Connection connection(String DbName) throws SQLException {
    //        return DriverManager.getConnection(SERVER_URL + DbName, USERNAME, PASSWORD);
    //    }

    //    public static Connection connection2(String DbName) {
    //        try {
    //            System.out.println(ColPrinter.brightGreen("Connection status :") + " Connection Established");
    //            return DriverManager.getConnection(SERVER_URL + DbName, USERNAME, PASSWORD);
    //        } catch (SQLException e) {
    //            throw new RuntimeException(buildSqlErrorMessage(e), e);
    //        }
    //    }

    // 3. Statement : créer une base de donnée.
    //    public static void createConnectionToANewDB(String dbName) {
    //        try (
    //                Connection connection = DriverManager.getConnection(SERVER_URL, USERNAME, PASSWORD);
    //                Statement statement = connection.createStatement();
    //        ) {
    //            statement.executeUpdate("CREATE DATABASE " + dbName);
    //            System.out.println("Database successfully created");
    //        } catch (SQLException e) {
    //            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
    //        }
    //    }

    // 4. Utiliser connection pour se connecter à la base de données.
    //  connection()

}
