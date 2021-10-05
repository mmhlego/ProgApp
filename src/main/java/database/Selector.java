package database;

import java.sql.ResultSet;

public class Selector {
    public static CustomResultSet Select(Tables table, String[] conditions, OrderBy[] orderBy,
            Arrangement[] arrangement) {
        String Sql = "SELECT * FROM " + table.toString() + " WHERE ";
        Sql += conditions[0];
        for (int i = 1; i < conditions.length; i++)
            Sql += " AND " + conditions[i];

        Sql += " ORDER BY " + orderBy[0].getValue() + " " + arrangement[0].getValue();
        for (int i = 1; i < orderBy.length; i++)
            Sql += " , " + orderBy[i].getValue() + " " + arrangement[i].getValue();

        return Convert(table, Connector.RunCommand(Sql));
    }

    public static CustomResultSet Select(Tables table, String[] conditions) {
        String Sql = "SELECT * FROM " + table.toString() + " WHERE ";
        Sql += conditions[0];
        for (int i = 1; i < conditions.length; i++)
            Sql += " AND " + conditions[i];

        return Convert(table, Connector.RunCommand(Sql));
    }

    public static CustomResultSet Convert(Tables table, ResultSet results) {
        return new CustomResultSet(table, results);
    }

    public static CustomResultSet Select(Tables table) {
        return Select(table, new String[] { "1" });
    }

    public enum OrderBy {
        ID("ID"), LastEdited("LastEdited");

        String Value;

        OrderBy(String v) {
            Value = v;
        }

        public String getValue() {
            return Value;
        }
    }

    public enum Arrangement {
        ASC("ASC"), DESC("DESC"), NONE("");

        String Value;

        Arrangement(String v) {
            Value = v;
        }

        public String getValue() {
            return Value;
        }
    }
}
