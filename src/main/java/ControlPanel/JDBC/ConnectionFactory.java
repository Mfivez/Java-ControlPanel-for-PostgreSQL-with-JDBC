package ControlPanel.JDBC;

import ControlPanel.UI.ColPrinter;

import java.sql.*;

/**
 * La classe {@code ConnectionFactory} fournit des méthodes utilitaires pour établir des connexions
 * avec une base de données PostgreSQL en utilisant JDBC, ainsi que pour créer des bases de données.
 *
 * <p>Avant d'utiliser ces méthodes, assurez-vous de configurer le fichier {@code pom.xml} de Maven
 * en y ajoutant la dépendance nécessaire pour le pilote PostgreSQL JDBC.</p>
 */
public abstract class ConnectionFactory {
    private static final String SERVER_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DB_URL = SERVER_URL + "training_jdbc_bd";
    private static final String DB_PG_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Dg3HIJ50__";


    public static String getServerURL() {
        return SERVER_URL;
    }

    public static String getPgDbURL() {
        return DB_PG_URL;
    }

    /**
     * Établit une connexion à la base de données PostgreSQL spécifiée.
     *
     * <p>Cette méthode utilise les paramètres de connexion (URL, nom d'utilisateur, mot de passe)
     * pour établir une connexion à la base de données en utilisant {@link DriverManager#getConnection(String, String, String)}.
     * </p>
     *
     * <p>Le mot-clé {@code throws SQLException} indique que cette méthode peut lever une exception
     * de type {@link SQLException}. Cette exception est lancée si une erreur survient lors de la
     * tentative de connexion à la base de données, par exemple, si les informations de connexion sont incorrectes
     * ou si la base de données n'est pas accessible.
     * </p>
     *
     * @return une instance de {@link Connection} représentant la connexion à la base de données
     * @throws SQLException si une erreur survient lors de la tentative de connexion à la base de données
     */
    public static Connection createDBConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    public static void getAllDb() {
        try (
                Connection connection = createDBConnection2(DB_PG_URL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT datname " +
                            "FROM pg_database " +
                            "WHERE datistemplate = false");
                ){
            int compteur = 0;
            System.out.println(ColPrinter.brightRed("Liste des bases de données sur le serveur postgres : "));
            while (resultSet.next()) {
                compteur++;
                System.out.println(ColPrinter.brightCyan("\t" + compteur + ". Database: ") + resultSet.getString("datname"));
            }

        } catch (SQLException e) {
            String errorMessage = "Failed: " + e.getMessage() + " (SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode() + ")";
            throw new RuntimeException(errorMessage, e);
        }
    }

    /**
     * Établit une connexion à la base de données PostgreSQL spécifiée, et affiche un message
     * indiquant que la connexion a été établie.
     *
     * <p>En cas de succès, un message "Connection Established" est affiché. Si une erreur survient
     * lors de la tentative de connexion, une {@link RuntimeException} est lancée avec un message détaillé
     * incluant les détails de la {@link SQLException}.
     * </p>
     *
     * <p>Le message d'erreur contient :</p>
     * <ul>
     *   <li>Le message spécifique de l'exception SQL via {@link SQLException#getMessage()}.</li>
     *   <li>Le code SQLState via {@link SQLException#getSQLState()} qui donne une indication
     *   standardisée sur le type d'erreur.</li>
     *   <li>Le code d'erreur spécifique au fournisseur de la base de données via {@link SQLException#getErrorCode()}.</li>
     * </ul>
     *
     * @return une instance de {@link Connection} représentant la connexion à la base de données
     * @throws RuntimeException si une erreur survient lors de la tentative de connexion à la base de données.
     * Le message d'erreur inclut les détails de la {@link SQLException}.
     */
    public static Connection createDBConnection2() {
        try {
            System.out.println(ColPrinter.brightGreen("Connection status :") + " Connection Established");
            return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(buildSqlErrorMessage(e), e);
        }
    }

    public static Connection createDBConnection2(String url) {
    try {
        Connection connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
        System.out.println(ColPrinter.brightGreen("Connection status :") + " Connection Established");
        return connection;
    } catch (SQLException e) {
        String errorMessage = "Data access failed: " + e.getMessage() + " (SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode() + ")";
        throw new RuntimeException(errorMessage, e);
    }
}


    /**
     * Établit une connexion au serveur PostgreSQL et crée une nouvelle base de données.
     *
     * <p>Cette méthode se connecte au serveur PostgreSQL sans spécifier de base de données
     * et crée une nouvelle base de données en utilisant l'instruction SQL {@code CREATE DATABASE}.
     * Si la base de données existe déjà, l'instruction est ignorée grâce au mot-clé {@code IF NOT EXISTS}.
     * </p>
     *
     * <p>En cas de succès, un message "Database successfully created" est affiché. Si une erreur survient
     * lors de la tentative de création de la base de données, une {@link RuntimeException} est lancée avec un message
     * détaillé incluant les détails de la {@link SQLException}.
     * </p>
     *
     * <p>Le message d'erreur contient :</p>
     * <ul>
     *   <li>Le message spécifique de l'exception SQL via {@link SQLException#getMessage()}.</li>
     *   <li>Le code SQLState via {@link SQLException#getSQLState()} qui donne une indication
     *   standardisée sur le type d'erreur.</li>
     *   <li>Le code d'erreur spécifique au fournisseur de la base de données via {@link SQLException#getErrorCode()}.</li>
     * </ul>
     *
     * @param dbName le nom de la base de données à créer
     * @return une instance de {@link Connection} représentant la connexion au serveur de base de données
     * @throws RuntimeException si une erreur survient lors de la tentative de création de la base de données.
     * Le message d'erreur inclut les détails de la {@link SQLException}.
     */
    public static void createConnectionToANewDB(String dbName) {
        try (
                Connection connection = DriverManager.getConnection(SERVER_URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate("CREATE DATABASE " + dbName);
            System.out.println("Database successfully created");
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    public static String buildSqlErrorMessage(SQLException e) {
        return String.format("Data access failed: %s (SQLState: %s, ErrorCode: %d)",
                e.getMessage(), e.getSQLState(), e.getErrorCode());
    }
}