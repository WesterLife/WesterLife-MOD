package fr.yan36.westerlife.common.objects;

import java.util.List;

public interface IDatabaseVariable {

    public static final String ID_ROW = "*_WesterLifeCustomIDROW_*";
    String tableName();
    List<String> getValues();
    RowDetails getIDRow();

    class RowDetails {
        public boolean doesIDRowExist;
        public String columnName;

        public RowDetails(String column, boolean doesIDRowExist) {
            this.columnName = column;
            this.doesIDRowExist = doesIDRowExist;
        }

        public String getColumnName() {
            return columnName;
        }

        public boolean isDoesIDRowExist() {
            return doesIDRowExist;
        }
    }
}
