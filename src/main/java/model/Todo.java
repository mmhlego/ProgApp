package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class Todo extends DUI {
    String ID;
    String Text;
    TodoLabels Label;

    public Todo(String iD, String text, TodoLabels label) {
        ID = iD;
        Text = text;
        Label = label;
    }

    public Todo(String iD, String text, String label) {
        this(iD, text, TodoLabels.valueOf(label));
    }

    public Todo(String text) {
        this(UUID.randomUUID().toString().replaceAll("-", ""), text, TodoLabels.Todo);
    }

    public Todo(String text, TodoLabels label) {
        this(UUID.randomUUID().toString().replaceAll("-", ""), text, label);
    }

    @Override
    public boolean DeleteRecord() {
        try {
            database.Connector.getConnection().prepareStatement("DELETE FROM `Todos` WHERE ID='" + ID + "'")
                    .executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean UpdateRecord() {
        String SQL = "UPDATE `Todos` SET Text='[Text]' ,Label='[Label]' WHERE ID='[ID]'";
        SQL = SQL.replace("[ID]", getID());
        SQL = SQL.replace("[Text]", getText());
        SQL = SQL.replace("[Label]", getLabel().toString());

        try {
            database.Connector.getConnection().prepareStatement(SQL).executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean InsertRecord() {
        try {
            PreparedStatement ps = database.Connector.getConnection()
                    .prepareStatement("INSERT INTO `Todos` (`ID`,`Text`,`Label`) VALUES (?,?,?)");
            ps.setString(1, getID());
            ps.setString(2, getText());
            ps.setString(3, getLabel().toString());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getID() {
        return ID;
    }

    public String getText() {
        return Text;
    }

    public TodoLabels getLabel() {
        return Label;
    }

    public void setLabel(TodoLabels label) {
        Label = label;
    }

    @Override
    public String toString() {
        return "[ ID: " + ID + ", Text: " + Text + ", Label: " + Label.toString() + "]\n";
    }
}
