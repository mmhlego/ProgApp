package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.CodeType;
import model.Language;
import model.Project;
import model.Todo;
import model.UsefulCode;

public class CustomResultSet {
    Tables DataMode;
    ResultSet Results;

    public CustomResultSet(Tables dataMode, ResultSet results) {
        DataMode = dataMode;
        Results = results;
    }

    public ArrayList<?> ToArrayList() {
        ResultSet temp = Results;
        ArrayList<Object> ans = new ArrayList<>();

        try {
            while (temp.next()) {
                ans.add(ConvertToCurrectForm(temp));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public Object GetFirstResult() {
        try {
            ResultSet temp = Results;
            if (temp.next())
                return ConvertToCurrectForm(temp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> GetColumn(int index) {
        ArrayList<String> ans = new ArrayList<>();
        ResultSet temp = Results;

        try {
            while (temp.next()) {
                ans.add(temp.getString(index));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ans;
    }

    public ArrayList<String> GetColumn(String columnName) {
        ArrayList<String> ans = new ArrayList<>();
        ResultSet temp = Results;

        try {
            while (temp.next()) {
                ans.add(temp.getString(columnName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ans;
    }

    private Object ConvertToCurrectForm(ResultSet results) throws SQLException {
        if (DataMode.equals(Tables.Projects)) {
            return new Project(results.getString(1), results.getString(2),
                    results.getString(3).replace("\r", "").replace("\n", ""), results.getString(4),
                    results.getString(5), Language.valueOf(results.getString(6)),
                    LocalDateTime.parse(results.getString(7)), LocalDateTime.parse(results.getString(8)),
                    results.getString(9));

        } else if (DataMode.equals(Tables.UsefulCodes)) {
            return new UsefulCode(results.getString(1), results.getString(4), results.getString(5),
                    results.getString(7), results.getString(8), Language.FromString(results.getString(2)),
                    CodeType.FromString(results.getString(3)),
                    new Gson().fromJson(results.getString(6), ArrayList.class));

        } else if (DataMode.equals(Tables.Todos)) {
            return new Todo(results.getString(1), results.getString(2), results.getString(3));

        }
        return null;
    }
}
