package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class Project extends DUI {
    private String ID, Name, Path, Version, Repository, Description;
    private Language language;
    private LocalDateTime Created, LastEdited;

    public Project(String id, String name, String path, String version, String repository, Language language,
            LocalDateTime created, LocalDateTime lastEdited, String description) {
        ID = id;
        Name = name;
        Path = path;
        Version = version;
        Repository = repository;
        this.language = language;
        Created = created;
        LastEdited = lastEdited;
        Description = description;
    }

    public Project(String id, String name, String path, String repository, Language language, String version) {
        this(id, name, path, version, repository, language, LocalDateTime.now(), LocalDateTime.now(), "");
    }

    public Project(String name, String path, String repository, Language language, String version) {
        this(GenerateID(language), name, path, repository, language, version);
    }

    public Project(String name, String path, Language language, String version) {
        this(name, path, "", language, version);
    }

    public Project(String name, String path, String repository, Language language) {
        this(name, path, repository, language, "1.0");
    }

    public Project(String name, String path, Language language) {
        this(name, path, "", language, "1.0");
    }

    public static String GenerateID(Language language) {
        return language.GetAbbreviation() + "-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    @Override
    public boolean DeleteRecord() {
        try {
            database.Connector.getConnection().prepareStatement("DELETE FROM `Projects` WHERE ID='" + ID + "'")
                    .executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean UpdateRecord() {
        String SQL = "UPDATE `Projects` SET Name='[Name]' ,Path='[Path]' ,Version='[Version]' ,Repository='[Repository]' ,Language='[Language]' ,Created='[Created]' ,LastEdited='[LastEdited]' ,Description='[Description]' WHERE ID='[ID]'";
        SQL = SQL.replace("[ID]", getID());
        SQL = SQL.replace("[Name]", getName());
        SQL = SQL.replace("[Path]", getPath());
        SQL = SQL.replace("[Version]", getVersion());
        SQL = SQL.replace("[Repository]", getRepository());
        SQL = SQL.replace("[Language]", getLanguage().toString());
        SQL = SQL.replace("[Created]", getCreated().toString());
        SQL = SQL.replace("[LastEdited]", getLastEdited().toString());
        SQL = SQL.replace("[Description]", getDescription());

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
                    "INSERT INTO `Projects`(`ID`,`Name`,`Path`,`Version`,`Repository`,`Language`,`Created`,`LastEdited`,`Description`) VALUES (?,?,?,?,?,?,?,?,?)");
            ps.setString(1, getID());
            ps.setString(2, getName());
            ps.setString(3, getPath());
            ps.setString(4, getVersion());
            ps.setString(5, getRepository());
            ps.setString(6, getLanguage().toString());
            ps.setString(7, getCreated().toString());
            ps.setString(8, getLastEdited().toString());
            ps.setString(9, getDescription());

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

    public String getName() {
        return Name;
    }

    public String getPath() {
        return Path;
    }

    public String getVersion() {
        return Version;
    }

    public String getRepository() {
        return Repository;
    }

    public Language getLanguage() {
        return language;
    }

    public LocalDateTime getCreated() {
        return Created;
    }

    public LocalDateTime getLastEdited() {
        return LastEdited;
    }

    public void UpdateLastEdited() {
        LastEdited = LocalDateTime.now();
        UpdateRecord();
    }

    public String getDescription() {
        return Description;
    }
}
