package BStorm.Utils;

public class ColumnMetaData {
    private final String columnType;
    private final boolean isAutoIncrement;

    public ColumnMetaData(String columnType, boolean isAutoIncrement) {
        this.columnType = columnType;
        this.isAutoIncrement = isAutoIncrement;
    }

    public String getColumnType() {
        return columnType;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

}
