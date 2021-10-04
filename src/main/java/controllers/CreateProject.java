package controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import model.Language;
import model.Project;
import stage.AlertType;
import stage.Overlay;
import tools.ProjectCreator;
import tools.Web;

public class CreateProject implements Initializable {
    @FXML
    private JFXTextField ProjectDisplayNameTF;
    @FXML
    private HBox ArtifactGroup;
    @FXML
    private JFXTextField ProjectArtifactTF;
    @FXML
    private JFXTextField ProjectPathTF;
    @FXML
    private JFXButton BrowsePathBTN;
    @FXML
    private JFXTextField VersionTF;
    @FXML
    private JFXTextField GitRepoNameTF;
    @FXML
    private JFXToggleButton InitializeToggle;
    @FXML
    private JFXTextField LanguageTF;
    @FXML
    private GridPane LanguagePanel;
    @FXML
    private VBox LanguageSettingsPanel;
    @FXML
    private Button CancelBTN;
    @FXML
    private Button CreateBTN;

    public static boolean ImportProject = false;
    int cols = 5;

    Language SelectedLanguage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InitializeToggle.setOnAction(e -> GitRepoNameTF.setDisable(!InitializeToggle.isSelected()));
        CreateBTN.setOnMouseClicked(e -> {
            if (Check()) {
                if (ImportProject) {
                    ImportProject();
                } else {
                    CreateNewProject();
                }
            }
        });
        CancelBTN.setOnMouseClicked(e -> Overlay.getOverlayStage().close());

        BrowsePathBTN.setOnMouseClicked(e -> {
            DirectoryChooser ds = new DirectoryChooser();
            ds.setTitle("Open Resource File");
            // ds.setInitialDirectory(new File(System.getProperty("user.dir")));

            File selectedFile = ds.showDialog(Overlay.getOverlayStage());

            ProjectPathTF.setText(selectedFile.getAbsolutePath() + "\\");
        });

        AddLanguages();

        if (ImportProject) {
            ((VBox) ArtifactGroup.getParent()).getChildren().remove(ArtifactGroup);
            CreateBTN.setText("Import Project");
        }
    }

    private boolean Check() {
        String Name = ProjectDisplayNameTF.getText();
        String ArtifactID = ImportProject ? "" : ProjectArtifactTF.getText();
        String Path = ProjectPathTF.getText();
        String Version = VersionTF.getText();
        String GitRepo = GitRepoNameTF.getText();

        if (Name.length() == 0) {
            Overlay.ShowAlert("Project name not specified.", AlertType.Error);
            return false;

        } else if (ArtifactID.length() == 0 && !ImportProject) {
            Overlay.ShowAlert("Project artifact id not specified.", AlertType.Error);
            return false;

        } else if (Path.length() == 0) {
            Overlay.ShowAlert("Project path not specified.", AlertType.Error);
            return false;

        } else if (Version.length() == 0) {
            Overlay.ShowAlert("Project version not specified.", AlertType.Error);
            return false;

        } else if (GitRepo.length() == 0 && InitializeToggle.isSelected()) {
            Overlay.ShowAlert("Project git repository not specified.", AlertType.Error);
            return false;

        } else if (LanguageTF.getText().length() == 0) {
            Overlay.ShowAlert("Project language not specified.", AlertType.Error);
            return false;

        } else if (!new File(Path).exists()) {
            Overlay.ShowAlert("Invalid project path.", AlertType.Error);
            return false;

        } else if ((!Web.IsUrlValid(GitRepo) || !Web.IsGitRepository(GitRepo)) && InitializeToggle.isSelected()) {
            Overlay.ShowAlert("Invalid git repository path.", AlertType.Error);
            return false;
        }

        return true;
    }

    private void CreateNewProject() {
        String Name = ProjectDisplayNameTF.getText();
        String ArtifactID = ProjectArtifactTF.getText();
        String Path = ProjectPathTF.getText();
        String Version = VersionTF.getText();
        String GitRepo = InitializeToggle.isSelected() ? GitRepoNameTF.getText() : "";

        if (Path.charAt(Path.length() - 1) != '\\')
            Path += "\\";

        String FullPath = Path + ArtifactID;

        new File(Path, ArtifactID).mkdir();
        ProjectCreator.CreateProject(Path, SelectedLanguage, ArtifactID, Version);

        if (InitializeToggle.isSelected()) {
            ProjectCreator.InitializeRepository(FullPath, GitRepo);
        }

        Project project = new Project(Name, FullPath, GitRepo, SelectedLanguage, Version);
        project.InsertRecord();

        Overlay.ShowAlert("Project created successfully.", AlertType.Success, () -> Overlay.getOverlayStage().close());
        ProjectManagerHeader.Search("");
    }

    private void ImportProject() {
        String Name = ProjectDisplayNameTF.getText();
        String Path = ProjectPathTF.getText();
        String Version = VersionTF.getText();
        String GitRepo = InitializeToggle.isSelected() ? GitRepoNameTF.getText() : "";

        if (InitializeToggle.isSelected()) {
            ProjectCreator.InitializeRepository(Path, GitRepo);
        }

        Project project = new Project(Name, Path, GitRepo, SelectedLanguage, Version);
        project.InsertRecord();

        Overlay.ShowAlert("Project imported successfully.", AlertType.Success, () -> Overlay.getOverlayStage().close());
        ProjectManagerHeader.Search("");
    }

    private void AddLanguages() {
        int index = cols;

        ArrayList<Language> languages = new ArrayList<>();

        if (ImportProject) {
            for (Language language : Language.values())
                languages.add(language);
        } else {
            languages = Language.getAvailableLanguages();
        }

        for (Language language : languages) {
            ImageView img = new ImageView(language.getImage());
            img.setFitHeight(80);
            img.setFitWidth(80);

            VBox container = new VBox(img);
            container.setAlignment(Pos.CENTER);
            container.setPadding(new Insets(5));

            container.setStyle(
                    "-fx-cursor: hand; -fx-border-radius: 10; -fx-background-radius:10; -fx-border-color: #B9D7EA; -fx-background-color:white; ");
            container.setOnMouseClicked(e -> {
                RemoveStyle();
                container.setStyle(
                        "-fx-cursor: hand; -fx-border-radius: 10; -fx-background-radius:10; -fx-border-color: #769FCD; -fx-background-color:#B9D7EA; ");
                LanguageTF.setText(language.getDisplayName());

                SelectedLanguage = language;
            });

            HBox box = new HBox();
            box.setAlignment(Pos.CENTER);
            box.setMinHeight(130);
            box.setPrefWidth(130);

            box.getChildren().addAll(new AnchorPane(container));
            LanguagePanel.add(box, index % cols, index / cols);

            index++;
        }
    }

    private void RemoveStyle() {
        for (Object box : LanguagePanel.getChildren()) {
            ((AnchorPane) ((HBox) box).getChildren().get(0)).getChildren().get(0).setStyle(
                    "-fx-cursor: hand; -fx-border-radius: 10; -fx-background-radius:10; -fx-border-color: #B9D7EA; -fx-background-color:white;");
        }
    }

    public static void setImportProject(boolean importProject) {
        ImportProject = importProject;
    }
}
