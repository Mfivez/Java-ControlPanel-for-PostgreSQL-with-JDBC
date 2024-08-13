package demo;

import ControlPanel.UI.ColPrinter;
import demo.Models.Address;
import demo.Models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/training_jdbc_bd";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Dg3HIJ50__";


    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String buildSQLErrorMessage(SQLException e) {
        return String.format("SQL ERROR: %s (SQLState: %s, ErrorCode: %d)",
                e.getMessage(), e.getSQLState(), e.getErrorCode());
    }

    public static void createTables() {
        try (
                Connection connection = connection();
                Statement statement = connection.createStatement();
                ) {
            //Créer la table
            statement.executeUpdate(User.queryCreateTable());
            statement.executeUpdate(Address.queryCreateTable());

        } catch (SQLException e) {
            throw new RuntimeException(buildSQLErrorMessage(e), e);
        }
    }

    public static void dropTables() {
        try (
                Connection connection = connection();
                Statement statement = connection.createStatement();
        ) {
            //Drop la table
            statement.execute("DROP TABLE address CASCADE");
            statement.execute("DROP TABLE \"user\" CASCADE");
            System.out.println(ColPrinter.brightGreen("Tables address et user vidées avec succès"));
        } catch (SQLException e) {
            throw new RuntimeException(buildSQLErrorMessage(e), e);
        }
    }

    public static void  createDropTable() {
        try (
                Connection connection = connection();
                Statement statement = connection.createStatement();
        ) {
            //Drop la table
            statement.execute("DROP TABLE address CASCADE");
            statement.execute("DROP TABLE \"user\" CASCADE");
            System.out.println(ColPrinter.brightGreen("Tables address et user vidées avec succès"));

            //Créer la table
            statement.executeUpdate(User.queryCreateTable());
            statement.executeUpdate(Address.queryCreateTable());

        } catch (SQLException e) {
            throw new RuntimeException(buildSQLErrorMessage(e), e);
        }
    }


}
