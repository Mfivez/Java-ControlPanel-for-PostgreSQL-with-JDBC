package BStorm.JDBC;

import BStorm.UI.ColPrinter;
import BStorm.Utils.ColumnMetaData;
import BStorm.Utils.PrepStatementUtils;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

//TODO : Créer pour les requêtes préparées une classe utilitaire qui gère la transformation des String en type
// correspondant au besoin des colonnes.

/**
 * La classe StatementFactory est une classe utilitaire pour interagir avec une base de données.
 * Elle permet de créer des tables, insérer, mettre à jour, sélectionner et supprimer des données,
 * ainsi que d'obtenir des informations sur les tables et les colonnes de la base de données.
 */
public class StatementFactory {
    Connection connection;
    Statement statement;
    boolean isStatementSimple;

    // region CONFIGURATION

    /**
     * Constructeur par défaut qui initialise une connexion avec la base de données en utilisant la méthode
     * createDBConnection2 de ConnectionFactory.
     */
    public StatementFactory() {
        setConnection(ConnectionFactory.createDBConnection2());
    }

    /**
     * Constructeur qui prend une connexion en paramètre.
     *
     * @param connection Une instance de la connexion à utiliser.
     */
    public StatementFactory(Connection connection) {
        setConnection(connection);
    }

    /**
     * Définit la connexion à utiliser et configure le type de Statement (simple ou non).
     *
     * @param connection L'objet Connection à utiliser.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
        setStatement(true);
    }

    /**
     * Crée et définit un Statement selon le type (simple ou non).
     *
     * @param simpleStatement Indique si le Statement doit être simple ou non.
     */
    public void setStatement(boolean simpleStatement) {
        this.statement = createStatement(simpleStatement);
        this.isStatementSimple = simpleStatement;
    }

    /**
     * Affiche la position actuelle de la connexion (le nom de la base de données).
     */
    public void getConnectionPosition() {
        try {
            System.out.println(connection.getCatalog());
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    //endregion

    //region PRIVATE METHODS

    /**
     * Crée un Statement en fonction du paramètre simpleStatement.
     *
     * @param simpleStatement Si vrai, crée un Statement simple, sinon un Statement avec
     *                        TYPE_SCROLL_INSENSITIVE et CONCUR_READ_ONLY.
     * @return Le Statement créé.
     */
    private Statement createStatement(boolean simpleStatement) {
        this.isStatementSimple = simpleStatement;
        try {
            return simpleStatement ? connection.createStatement()
                    : connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch( SQLException e ) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    /**
     * Exécute une requête SQL.
     *
     * @param query La requête SQL à exécuter.
     * @return Le ResultSet si la requête est une requête de sélection, sinon null.
     */
    private ResultSet executeQuery(String query) {
        System.out.println(query);
        try {
            if (isStatementSimple) { statement.executeUpdate(query); return null; }
            else { return statement.executeQuery(query); }
        } catch( SQLException e ) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    /**
     * Dessine les données d'un ResultSet sous forme de tableau.
     *
     * @param rs Le ResultSet contenant les données à afficher.
     */
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

            printTable(columnNames, columnWidths, rows);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

    /**
     * Imprime un tableau formaté à partir des données.
     *
     * @param columnNames Les noms des colonnes.
     * @param columnWidths Les largeurs des colonnes.
     * @param rows Les lignes de données.
     */
    private void printTable(String[] columnNames, int[] columnWidths, List<String[]> rows) {
        printSeparator(columnWidths);
        printRow(columnNames, columnWidths, true, true);
        printSeparator(columnWidths);

        boolean isBrightRow = false;
        for (String[] row : rows) {
            printRow(row, columnWidths, false, isBrightRow);
            isBrightRow = !isBrightRow;
        }
        printSeparator(columnWidths);
    }

    /**
     * Imprime une ligne de séparation dans le tableau.
     *
     * @param columnWidths Les largeurs des colonnes.
     */
    private void printSeparator(int[] columnWidths) {
        System.out.print(ColPrinter.brightYellow("+"));
        for (int width : columnWidths) {
            System.out.print(ColPrinter.brightYellow("-").repeat(width + 2) + ColPrinter.brightYellow("+"));
        }
        System.out.println();
    }

    /**
     * Imprime une ligne du tableau.
     *
     * @param row La ligne à imprimer.
     * @param columnWidths Les largeurs des colonnes.
     * @param isTitle Indique si la ligne est un titre.
     * @param isBrightRow Indique si la ligne doit être en surbrillance.
     */
    private void printRow(String[] row, int[] columnWidths, boolean isTitle, boolean isBrightRow) {
        System.out.print(ColPrinter.brightYellow("|"));

        for (int i = 0; i < row.length; i++) {
            String cell = row[i] != null ? row[i] : "NULL";
            String formattedCell = String.format(" %-" + columnWidths[i] + "s ", cell);

            if (isTitle) {
                System.out.print(ColPrinter.brightGreen(formattedCell));
            } else {
                System.out.print(isBrightRow ? ColPrinter.white(formattedCell) : ColPrinter.brightWhite(formattedCell));
            }
            System.out.print(ColPrinter.brightYellow("|"));
        }
        System.out.println();
    }

    /**
     * Exécute une requête et retourne un ResultSet.
     *
     * @param query La requête à exécuter.
     * @return Le ResultSet résultant de l'exécution de la requête.
     */
    private ResultSet executeReturnQuery(String query) {
        setStatement(false);
        try {
            return executeQuery(query);
        } finally {
            setStatement(true);
        }
    }

    /**
     * Récupère les types de colonnes d'une table donnée.
     *
     * @param tableName Le nom de la table.
     * @return Une map contenant les noms des colonnes et leurs métadonnées.
     */
    private Map<String, ColumnMetaData> getColumnTypes(String tableName) {
        String sql = "SELECT * FROM \"" + tableName + "\" LIMIT 1";
        try ( ResultSet rs = statement.executeQuery(sql)) {
            return extractColumnMetaData(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extrait les métadonnées des colonnes à partir d'un ResultSet.
     *
     * @param rs Le ResultSet à partir duquel extraire les métadonnées.
     * @return Une map contenant les noms des colonnes et leurs métadonnées.
     * @throws SQLException En cas d'erreur SQL.
     */
    private Map<String, ColumnMetaData> extractColumnMetaData(ResultSet rs) throws SQLException {
        Map<String, ColumnMetaData> columnTypes = new HashMap<>();
        var metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            String columnType = metaData.getColumnTypeName(i);
            boolean isAutoIncrement = metaData.isAutoIncrement(i);
            if (!isAutoIncrement) {
                columnTypes.put(columnName, new ColumnMetaData(columnType, isAutoIncrement));
            }
        }
        return columnTypes;
    }

    //endregion

    //region PUBLIC METHODS

    /**
     * Crée une table dans la base de données.
     *
     * @param tableName le nom de la table
     * @param columns la définition des colonnes, par exemple :
     *                "id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL UNIQUE"
     */
    public void createTable(String tableName, String... columns) {
        String query = String.format("CREATE TABLE \"%s\" (%s)", tableName, String.join(", ", columns));
        executeQuery(query);
        System.out.println("La table " + tableName + " a été créée avec succès");
    }

    /**
     * Supprime une table de la base de données.
     *
     * @param tableName le nom de la table à supprimer
     */
    public void deleteTable(String tableName) {
        executeQuery(String.format("DROP TABLE \"%s\";", tableName));
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
        Map<String, ColumnMetaData> columnTypes = getColumnTypes(tableName);
        List<String> columnsList = Arrays.stream(columnNames.split(","))
                .map(String::trim)
                .filter(columnName -> !columnTypes.get(columnName).isAutoIncrement())
                .toList();

        String filteredColumnNames = String.join(", ", columnsList);
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO \"")
                .append(tableName).append("\" (").append(filteredColumnNames)
                .append(") VALUES ");

        List<String[]> valuesList = Arrays.stream(data.split(" & "))
                .map(entry -> entry.split(", "))
                .toList();

        String valuesPlaceholder = valuesList.stream()
                .map(_ -> "(" + String.join(", ", Collections.nCopies(columnsList.size(), "?")) + ")")
                .collect(Collectors.joining(", "));

        queryBuilder.append(valuesPlaceholder);


        try (PreparedStatement pstmt = connection.prepareStatement(queryBuilder.toString())) {
            setPreparedStatementValues(pstmt, valuesList, columnsList, columnTypes);
            pstmt.executeUpdate();
            System.out.println(queryBuilder);
            System.out.println("La table " + tableName + " a été modifiée avec succès");
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    /**
     * Définit les valeurs des paramètres d'un {@code PreparedStatement}.
     *
     * @param pstmt l'objet {@code PreparedStatement}
     * @param valuesList les valeurs à définir
     * @param columnsList les noms des colonnes
     * @param columnTypes les métadonnées des colonnes
     * @throws SQLException si une erreur SQL se produit
     */
    private void setPreparedStatementValues(PreparedStatement pstmt, List<String[]> valuesList, List<String> columnsList, Map<String, ColumnMetaData> columnTypes) throws SQLException {
        int paramIndex = 1;
        for (String[] values : valuesList) {
            for (int i = 0; i < values.length; i++) {
                String columnName = columnsList.get(i);
                ColumnMetaData columnMetaData = columnTypes.get(columnName);
                Object convertedValue = PrepStatementUtils.convertValue(values[i], columnMetaData.getColumnType());
                PrepStatementUtils.setValue(pstmt, paramIndex++, convertedValue);
            }
        }
    }

    /**
     * Met à jour les données dans une table spécifiée en fonction des conditions fournies.
     *
     * @param tableName le nom de la table
     * @param columnNameAndValue les colonnes et les nouvelles valeurs, format : "colonne1=valeur1, colonne2=valeur2"
     * @param condition la condition pour la mise à jour des données
     */
    public void updateData(String tableName,String columnNameAndValue, String condition) {
        String[] pairs = columnNameAndValue.split(",");
        StringBuilder sb = new StringBuilder("UPDATE \"").append(tableName).append("\" SET ");

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

    /**
     * Sélectionne et affiche les données d'une table spécifiée en fonction des conditions et des limites fournies.
     *
     * @param tableName le nom de la table
     * @param limit la limite du nombre de résultats (vide pour aucun lim
     * @param condition la condition pour la sélection des données (vide pour aucune condition)
     */
    public void selectData(String tableName, String limit, String condition) {
        String lim = limit.isEmpty() ? null : limit;
        String cond = condition.isEmpty() ? null : condition;

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM \"").append(tableName).append("\"");
        if (cond != null) queryBuilder.append(" WHERE ").append(cond);
        if (lim != null) queryBuilder.append(" LIMIT ").append(lim);

        try {
            ResultSet rs = executeReturnQuery(queryBuilder.toString());
            if (rs != null) {
                System.out.println(ColPrinter.brightGreen("Affichage des données pour la requête : "));
                drawData(rs);
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    /**
     * Supprime les données d'une table spécifiée en fonction de la condition fournie.
     *
     * @param tableName le nom de la table
     * @param condition la condition pour la suppression des données (vide pour aucune condition)
     */
    public void deleteData(String tableName, String condition) {
        String cond = condition.isEmpty() ? null : condition;
        StringBuilder sb = new StringBuilder("DELETE FROM \"").append(tableName).append("\"");
        if (cond != null) sb.append(" WHERE ").append(cond);
        executeQuery(sb.toString());
        System.out.println("Les données de la table " + tableName + " ont été supprimées avec succès.");
    }

    /**
     * Affiche la liste des tables dans la base de données spécifiée.
     *
     * @param DbName le nom de la base de données
     */
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
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    /**
     * Affiche la liste des colonnes d'une table spécifiée dans la base de données.
     *
     * @param tableName le nom de la table
     */
    public void getColumnList(String tableName) {
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
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    /**
     * Supprime une base de données spécifiée, à condition qu'elle ne soit pas la base de données "postgres".
     *
     * @param dbName le nom de la base de données à supprimer
     */
    public void dropDataBase(String dbName) {
        if (!dbName.equalsIgnoreCase("postgres")) {
            String query = "DROP DATABASE IF EXISTS \"" + dbName + "\";";
            executeQuery(query);
            System.out.println("La base de données " + dbName + " a été supprimée avec succès");
        } else System.out.println(ColPrinter.red("Hep, hep, hep, on ne supprime pas la table postgres ! "));
    }
    //endregion
}
