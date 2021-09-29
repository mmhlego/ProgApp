package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;

public class UsefulCode extends DUI {
    String ID, Code, Description, Input, Output;
    Language language;
    CodeType Type;
    ArrayList<String> Tags;

    public UsefulCode(String iD, String code, String description, String input, String output, Language language,
            CodeType type, ArrayList<String> tags) {
        ID = iD;
        Code = code;
        Description = description;
        Input = input;
        Output = output;
        this.language = language;
        Type = type;
        Tags = tags;
    }

    public UsefulCode(String code, String description, String input, String output, Language language, CodeType type,
            ArrayList<String> tags) {
        this(GenerateID(language, type), code, description, input, output, language, type, tags);
    }

    public UsefulCode(String code, String description, Language language, CodeType type, ArrayList<String> tags) {
        this(GenerateID(language, type), code, description, "", "", language, type, tags);
    }

    public static String GenerateID(Language language, CodeType type) {
        return language.GetAbbreviation() + "-" + type.toString() + "-"
                + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    @Override
    public boolean DeleteRecord() {
        try {
            database.Connector.getConnection().prepareStatement("DELETE FROM `UsefulCodes` WHERE ID='" + getID() + "'")
                    .executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean UpdateRecord() {
        String SQL = "UPDATE `UsefulCodes` SET Language='[Language]' ,Type='[Type]' ,Code='[Code]' ,Description='[Description]' ,Tags='[Tags]' ,Input='[Input]' ,Output='[Output]' WHERE ID='[ID]'";
        SQL = SQL.replace("[Language]", getLanguage().toString());
        SQL = SQL.replace("[Type]", getType().toString());
        SQL = SQL.replace("[Code]", getCode());
        SQL = SQL.replace("[Description]", getDescription());
        SQL = SQL.replace("[Tags]", new Gson().toJson(getTags()));
        SQL = SQL.replace("[Input]", getInput());
        SQL = SQL.replace("[Output]", getOutput());
        SQL = SQL.replace("[ID]", getID());
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
            PreparedStatement ps = database.Connector.getConnection().prepareStatement(
                    "INSERT INTO `UsefulCodes`(`ID`,`Language`,`Type`,`Code`,`Description`,`Tags`,`Input`,`Output`) VALUES (?,?,?,?,?,?,?,?)");
            ps.setString(1, getID());
            ps.setString(2, getLanguage().toString());
            ps.setString(3, getType().toString());
            ps.setString(4, getCode());
            ps.setString(5, getDescription());
            ps.setString(6, new Gson().toJson(getTags()));
            ps.setString(7, getInput());
            ps.setString(8, getOutput());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getCode() {
        return Code;
    }

    public String getDescription() {
        return Description;
    }

    public String getID() {
        return ID;
    }

    public String getInput() {
        return Input;
    }

    public Language getLanguage() {
        return language;
    }

    public String getOutput() {
        return Output;
    }

    public ArrayList<String> getTags() {
        return Tags;
    }

    public CodeType getType() {
        return Type;
    }
}
