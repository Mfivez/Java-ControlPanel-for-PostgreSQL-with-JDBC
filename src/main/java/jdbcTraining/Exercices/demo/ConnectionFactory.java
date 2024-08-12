package jdbcTraining.Exercices.demo;

import jdbcTraining.Exercices.demo.Entity.Address;
import jdbcTraining.Exercices.demo.Entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/training_jdbc_bd";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Dg3HIJ50__";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String buildSqlErrorMessage(SQLException e) {
        return String.format("SQL ERROR: %s (SQLState: %s, ErrorCode: %d)",
                e.getMessage(), e.getSQLState(), e.getErrorCode());
    }

    public static void createTables() {
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {

            // Vider les tables existantes
            stmt.execute("DROP TABLE address CASCADE;");
            stmt.execute("DROP TABLE \"user\" CASCADE;");
            System.out.println("Tables 'address' et 'user' vidées avec succès.");

            // Création de la table utilisateur
            stmt.execute(User.createTableJdbc());
            System.out.println("Table 'utilisateur' créée avec succès.");

            // Création de la table adresse
            stmt.execute(Address.createTableJdbc());
            System.out.println("Table 'adresse' créée avec succès.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}