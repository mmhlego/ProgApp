package controllers;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Editors;
import model.Project;
import tools.Cmd;
import tools.Web;

public class EachProject {
    @FXML
    private VBox InformationPanel;
    @FXML
    private Label ProjectPathLBL;
    @FXML
    private Label ProjectCreatedLBL;
    @FXML
    private Label ProjectEditedLBL;
    @FXML
    private Label ProjectRepoLBL;
    @FXML
    private ImageView OpenRepoInGithubBTN;
    @FXML
    private TextArea DescriptionTA;
    @FXML
    private HBox EditorsBox;
    @FXML
    private Button DeleteProjectBTN;
    @FXML
    private HBox SmallInfoPanel;
    @FXML
    private Label ProjectNameLBL;
    @FXML
    private Label ProjectLanguageLBL;
    @FXML
    private ImageView ProjectLanguageIMG;
    @FXML
    private Label ProjectVersionLBL;
    @FXML
    private VBox GitConnectionBox;
    @FXML
    private ImageView ProjectInformationBTN;

    static Editors[] AvailableEditors;
    static boolean FirstRun = true;

    Project project;
    boolean ProjectExists;
    int duration = 300;
    double maxHeight = 585;

    public void setProject(Project p) {
        if (FirstRun) {
            FirstRun = false;
            AvailableEditors = Editors.GetAvailablEditors();
        }

        project = p;

        ProjectExists = new File(project.getPath()).exists();

        DesignPage();
        BindSizes();

        ToggleInformationPanel();
        if (!ProjectExists)
            NotFound();

        DeleteProjectBTN.setOnMouseClicked(e -> {
            project.DeleteRecord();
            ProjectManagerHeader.Search("");
        });

        ProjectInformationBTN.setOnMouseClicked(e -> ToggleInformationPanel());
    }

    private void DesignPage() {
        ProjectNameLBL.setText(project.getName());
        ProjectLanguageLBL.setText(project.getLanguage().getDisplayName());
        ProjectLanguageIMG.setImage(project.getLanguage().getImage());
        ProjectVersionLBL.setText(project.getVersion());

        OpenRepoInGithubBTN.setOnMouseClicked(e -> Web.OpenWebpage(project.getRepository()));

        ProjectPathLBL.setText(project.getPath());
        ProjectCreatedLBL
                .setText(project.getCreated().format(DateTimeFormatter.ofPattern("yy/MM/dd - HH:mm")).toString());
        ProjectEditedLBL
                .setText(project.getLastEdited().format(DateTimeFormatter.ofPattern("yy/MM/dd - HH:mm")).toString());

        if (ProjectExists) {
            AddEditors();
        } else {
            InformationPanel.getChildren().remove(4);
            maxHeight -= 160;
        }

        try {
            if (!project.getDescription().equals("")) {
                DescriptionTA.setText(project.getDescription());
            } else {
                InformationPanel.getChildren().remove(3);
                maxHeight -= 140;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!project.getRepository().equals("")) {
                GitConnectionBox.getChildren()
                        .add(new FXMLLoader(new File("resources/components/GitConnected.fxml").toURI().toURL()).load());
                ProjectRepoLBL.setText(project.getRepository());
            } else {
                maxHeight -= 60;
                GitConnectionBox.getChildren().add(
                        new FXMLLoader(new File("resources/components/GitNotConnected.fxml").toURI().toURL()).load());
                InformationPanel.getChildren().remove(2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void BindSizes() {
        InformationPanel.prefWidthProperty().bind(stage.StageManager.getListBox().prefWidthProperty().subtract(100));
        SmallInfoPanel.prefWidthProperty().bind(stage.StageManager.getListBox().prefWidthProperty().subtract(50));
        DeleteProjectBTN.prefWidthProperty().bind(InformationPanel.prefWidthProperty().subtract(50));

        ProjectNameLBL.prefWidthProperty().bind(SmallInfoPanel.prefWidthProperty().subtract(550));
    }

    private void AddEditors() {
        for (int i = 0; i < AvailableEditors.length; i++) {
            Editors editor = AvailableEditors[i];

            try {
                ImageView img = new ImageView(editor.GetImage());
                img.setFitWidth(80);
                img.setFitHeight(80);

                AnchorPane imgContainer = new AnchorPane(img);
                imgContainer.setMaxWidth(80);
                imgContainer.setMaxHeight(80);
                imgContainer.setStyle("-fx-cursor: hand;");
                imgContainer.setOnMouseClicked(e -> OpenProjectInEditor(editor.getPrefix()));

                EditorsBox.getChildren().add(imgContainer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void OpenProjectInEditor(String prefix) {
        Cmd.RunCommand(prefix + " .", project.getPath());
        project.UpdateLastEdited();
        ProjectEditedLBL
                .setText(project.getLastEdited().format(DateTimeFormatter.ofPattern("yy/MM/dd - HH:mm")).toString());
        ProjectManagerHeader.Search("");
    }

    private void ToggleInformationPanel() {
        Timeline animation = new Timeline();

        boolean isOpen = (InformationPanel.getPrefHeight() != 0.0);

        if (!isOpen) {
            animation.getKeyFrames().add(new KeyFrame(Duration.millis(duration),
                    new KeyValue(InformationPanel.prefHeightProperty(), maxHeight)));
        }

        for (Node node : InformationPanel.getChildren()) {
            if (node instanceof HBox) {
                animation.getKeyFrames().add(new KeyFrame(Duration.millis(isOpen ? 1 : duration),
                        new KeyValue(((HBox) node).visibleProperty(), !isOpen)));
            } else if (node instanceof VBox) {
                animation.getKeyFrames().add(new KeyFrame(Duration.millis(isOpen ? 1 : duration),
                        new KeyValue(((VBox) node).visibleProperty(), !isOpen)));
            } else if (node instanceof Group) {
                animation.getKeyFrames().add(new KeyFrame(Duration.millis(isOpen ? 1 : duration),
                        new KeyValue(((Group) node).visibleProperty(), !isOpen)));
            }
        }

        if (isOpen) {
            animation.getKeyFrames().add(
                    new KeyFrame(Duration.millis(duration), new KeyValue(InformationPanel.prefHeightProperty(), 0.0)));
        }

        animation.play();
    }

    private void NotFound() {
        ProjectInformationBTN.getParent()
                .setStyle(ProjectInformationBTN.getParent().getStyle().replace("white", "#bbb"));
        InformationPanel.setStyle(InformationPanel.getStyle().replace("#ffffff", "#bbbbbb"));

        ProjectPathLBL.setStyle(ProjectPathLBL.getStyle().replace("white", "#ccc"));
        ProjectCreatedLBL.setStyle(ProjectCreatedLBL.getStyle().replace("white", "#ccc"));
        ProjectEditedLBL.setStyle(ProjectEditedLBL.getStyle().replace("white", "#ccc"));
        ProjectRepoLBL.setStyle(ProjectRepoLBL.getStyle().replace("white", "#ccc"));
        DescriptionTA.setStyle(DescriptionTA.getStyle().replace("white", "#ccc"));
    }
}
