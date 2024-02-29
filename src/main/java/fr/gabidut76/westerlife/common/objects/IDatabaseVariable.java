package fr.gabidut76.westerlife.common.objects;

import java.util.List;

public interface IDatabaseVariable {

    public static final String ID_ROW = "*_WesterLifeCustomIDROW_*";
    String tableName();
    List<String> getValues();
    RowDetails getIDRow();


    class RowDetails {
        public boolean doesIDRowExist;
        public String columnName;
        public boolean special;

        public RowDetails(String column, boolean doesIDRowExist, boolean special) {
            this.columnName = column;
            this.doesIDRowExist = doesIDRowExist;
            this.special = special;
        }

        public String getColumnName() {
            return columnName;
        }

        public boolean isDoesIDRowExist() {
            return doesIDRowExist;
        }

        public boolean isSpecial() {
            return special;
        }
    }
}
