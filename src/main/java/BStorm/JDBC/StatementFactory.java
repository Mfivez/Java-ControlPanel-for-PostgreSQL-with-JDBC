package BStorm.JDBC;

import BStorm.UI.ColPrinter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * Exercice 1 :
 * 1. Créer une table dans la base de donnée qui a été créé pendant la démonstration.
 *      Celle-ci portera le nom User et comportera les colonnes : id, name, email.
 * 2. Ajouter des données à l'intérieur
 * 3. Modifier des données à l'intérieur
 * 4. Supprimer les données à l'intérieur
 */
public class StatementFactory {
    Connection connection;
    Statement statement;
    boolean isStatementSimple;

    // region CONFIGURATION

    public StatementFactory() {
        this.connection = ConnectionFactory.createDBConnection2();
        this.statement = getStatement(true);
        this.isStatementSimple = true;
    }

    public StatementFactory(Connection connection) {
        this.connection = connection;
        this.statement = getStatement(true);
        this.isStatementSimple = true;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        this.statement = getStatement(true);
        this.isStatementSimple = true;
    }

    public void setStatement(boolean simpleStatement) {
        this.statement = getStatement(simpleStatement);
        this.isStatementSimple = simpleStatement;
    }

    public void getConnection() {
        try {
            System.out.println(connection.getCatalog());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //endregion

    //region PRIVATE METHODS

    private Statement getStatement(boolean simpleStatement) {
        this.isStatementSimple = simpleStatement;
        try {
            if (simpleStatement) {
                return connection.createStatement();
            } else {
                return connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }
        } catch( SQLException e ) {
            String errorMessage = "Data access failed: " + e.getMessage() + " (SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode() + ")";
            throw new RuntimeException(errorMessage, e);
        }
    }

    private ResultSet executeQuery(String query) {
        System.out.println(query);
        try {
            if (isStatementSimple) { statement.executeUpdate(query); return null; }
            else { return statement.executeQuery(query); }

        } catch( SQLException e ) {
            String errorMessage = "Data access failed: " + e.getMessage() + " (SQLState: " + e.getSQLState() + ")";
            throw new RuntimeException(errorMessage, e);
        }
    }

    private void drawData(ResultSet rs) {
        try  {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            String[] columnNames = new String[columnsNumber];
            int[] columnWidths = new int[columnsNumber];
            for (int i = 1; i <= columnsNumber; i++) {
                columnNames[i - 1] = rsmd.getColumnName(i);
                columnWidths[i - 1] = columnNames[i - 1].length();
            }

            List<String[]> rows = new ArrayList<>();
            while (rs.next()) {
                String[] row = new String[columnsNumber];
                for (int i = 1; i <= columnsNumber; i++) {
                    row[i - 1] = rs.getString(i);
                    columnWidths[i - 1] = Math.max(columnWidths[i - 1], row[i - 1] != null ? row[i - 1].length() : 0);
                }
                rows.add(row);
            }

            printSeparator(columnWidths);
            printRow(columnNames, columnWidths, true, true);
            printSeparator(columnWidths);

            boolean isBrightRow = false;
            for (String[] row : rows) {
                printRow(row, columnWidths, false, isBrightRow);
                isBrightRow = !isBrightRow;
            }
            printSeparator(columnWidths);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

    private void printSeparator(int[] columnWidths) {
        System.out.print(ColPrinter.brightYellow("+"));
        for (int width : columnWidths) {
            System.out.print(ColPrinter.brightYellow("-").repeat(width + 2) + ColPrinter.brightYellow("+"));
        }
        System.out.println();
    }

    private void printRow(String[] row, int[] columnWidths, boolean isTitle, boolean isBrightRow) {
        System.out.print(ColPrinter.brightYellow("|"));

        for (int i = 0; i < row.length; i++) {
            String cell = row[i] != null ? row[i] : "NULL";
            String formattedCell = String.format(" %-" + columnWidths[i] + "s ", cell);

            if (isTitle) {
                System.out.print(ColPrinter.brightGreen(formattedCell));
            } else {
                if (isBrightRow) {
                    System.out.print(ColPrinter.white(formattedCell));
                } else {
                    System.out.print(ColPrinter.brightWhite(formattedCell));
                }
            }
            System.out.print(ColPrinter.brightYellow("|"));
        }
        System.out.println();
    }

    private ResultSet executeReturnQuery(String query) {
        setStatement(false);
        try {
            return executeQuery(query);
        } finally {
            setStatement(true);
        }
    }

    //endregion

    //region PUBLIC METHODS

    /**
     *
     * @param tableName Nom de la table
     * @param columns Nom des colonnes et informations sur celles-ci -> Exemple :
     *                    id SERIAL PRIMARY KEY,
     *                    name VARCHAR(255) NOT NULL,
     *                    email VARCHAR(255) NOT NULL UNIQUE
     */
    public void createTable(String tableName, String... columns) {
        getConnection();
        StringBuilder sb = new StringBuilder("CREATE TABLE \"" + tableName + "\"(");
        Arrays.stream(columns).forEach(sb::append);
        sb.append(")");
        executeQuery(sb.toString());
        System.out.println("La table " + tableName + " a été créée avec succès");
    }

    public void deleteTable(String tableName) {
        executeQuery("DROP TABLE \"" + tableName + "\";");
        System.out.println("La table " + tableName + " a été supprimée avec succès");
    }

    /**
     * Insère des données dans une table spécifiée.
     *
     * @param tableName le nom de la table
     * @param columnNames les noms des colonnes, par exemple : "name, email"
     * @param data les données à insérer, chaque ensemble de valeurs doit être séparé par '&' :
     *             Exemple : "Alice, alice@example.com & Bob, bob@example.com"
     */
    public void insertData(String tableName, String columnNames, String data) {
        StringBuilder sb = new StringBuilder("INSERT INTO \"" + tableName + "\" (" + columnNames + ") VALUES ");

        String formattedData = Arrays.stream(data.split(" & "))
                .map(entry -> {
                    String[] values = entry.split(", ");
                    return "(" + Arrays.stream(values)
                            .map(value -> "'" + value + "'")
                            .collect(Collectors.joining(", ")) + ")";
                })
                .collect(Collectors.joining(", "));

        sb.append(formattedData);
        executeQuery(sb.toString());
        System.out.println("La table " + tableName + " a été modifiée avec succès");
    }

    public void updateData(String tableName,String columnNameAndValue, String condition) {
        String[] pairs = columnNameAndValue.split(",");
        StringBuilder sb = new StringBuilder("UPDATE \"" + tableName + "\" SET ");
        for (int i = 0; i < pairs.length; i++) {
            String[] pair = pairs[i].split("=");
            String column = pair[0].trim();
            String value = pair[1].trim();
            sb.append(column).append("='").append(value).append("'");
            if (i < pairs.length - 1) { sb.append(", "); }
        }
        sb.append(" WHERE ").append(condition);
        executeQuery(sb.toString());
        System.out.println("La table " + tableName + " a été mise à jour avec succès");
    }

    public void selectData(String tableName, String limit, String condition) {
        String lim = limit.isEmpty() ? null : limit;
        String cond = condition.isEmpty() ? null : condition;

        StringBuilder sb = new StringBuilder("SELECT * FROM \"" + tableName + "\"");
        if (cond != null) sb.append(" WHERE ").append(cond);
        if (lim != null) sb.append(" LIMIT ").append(lim);

        try {
            ResultSet rs = executeReturnQuery(sb.toString());
            if (rs != null) {
                System.out.println(ColPrinter.brightGreen("Affichage des données pour la requête : "));
                drawData(rs);
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteData(String tableName, String condition) {
        String cond = condition.isEmpty() ? null : condition;

        StringBuilder sb = new StringBuilder("DELETE FROM \"" + tableName + "\"");
        if (cond != null) sb.append(" WHERE ").append(cond);

        executeQuery(sb.toString());
        System.out.println("Les données de la table " + tableName + " ont été supprimées avec succès.");
    }

    public void getTableList(String DbName) {
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet tables = dbmd.getTables(null, null, "%", types);
            int compteur = 0;
            System.out.println(ColPrinter.brightBlue("Tables dans la base de données " + DbName + " :") );
            while (tables.next()) {
                compteur++;
                System.out.println(ColPrinter.brightRed("\t" + compteur + "."+ tables.getString("TABLE_NAME")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getColumnList(String dbName, String tableName) {
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet columns = dbmd.getColumns(null, null, tableName, null);
            int compteur = 0;
            System.out.println(ColPrinter.brightBlue("Colonnes de la table " + tableName + " :"));
            while (columns.next()) {
                compteur++;
                String columnName = columns.getString("COLUMN_NAME");
                String columnType = columns.getString("TYPE_NAME");
                int columnSize = columns.getInt("COLUMN_SIZE");
                System.out.println(ColPrinter.brightRed("\t" + compteur + ". " + columnName + " - " + columnType + " (" + columnSize + ")"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropDataBase(String dbName) {
        if (!dbName.equalsIgnoreCase("postgres")) {
            String query = "DROP DATABASE IF EXISTS \"" + dbName + "\";";
            executeQuery(query);
            System.out.println("La base de données " + dbName + " a été supprimée avec succès");
        } else System.out.println(ColPrinter.red("Hep, hep, hep, on ne supprime pas la table postgres ! "));
    }

    //endregion
}
