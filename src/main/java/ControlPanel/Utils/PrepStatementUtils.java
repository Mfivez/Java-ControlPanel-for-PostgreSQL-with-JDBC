package ControlPanel.Utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class PrepStatementUtils {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static Integer toInteger(String value) throws NumberFormatException {
        return Integer.valueOf(value);
    }

    private static Double toDouble(String value) throws NumberFormatException {
        return Double.valueOf(value);
    }

    private static Date toDate(String value) throws ParseException {
        return DATE_FORMAT.parse(value);
    }

    private static String toString(String value) {
        return value;
    }

    private static Long toLong(String value) throws NumberFormatException {
        return Long.valueOf(value);
    }

    public static Object convertValue(String value, String sqlType) {
        try {
            return switch (sqlType.toUpperCase()) {
                case "INTEGER" -> toInteger(value);
                case "SERIAL" -> null;
                case "FLOAT", "DOUBLE" -> toDouble(value);
                case "DATE" -> toDate(value);
                case "VARCHAR", "CHAR" -> toString(value);
                default -> throw new IllegalArgumentException("Type non pris en charge : " + sqlType);
            };
        } catch (Exception e) {
            throw new IllegalArgumentException("Type non pris en charge : " + sqlType);
        }
    }

    public static void setValue(PreparedStatement pstmt, int paramIndex, Object value) {
        try {
            switch (value) {
                case null -> { return; }
                case Integer i -> pstmt.setInt(paramIndex, i);
                case Double v -> pstmt.setDouble(paramIndex, v);
                case Date date -> pstmt.setDate(paramIndex, new java.sql.Date(date.getTime()));
                case String s -> pstmt.setString(paramIndex, s);
                default -> throw new SQLException("Type non pris en charge pour la valeur : " + value);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
