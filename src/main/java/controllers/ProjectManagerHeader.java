package controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import database.Selector.Arrangement;
import database.Selector.OrderBy;
import database.Tables;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Project;
import stage.Overlay;
import stage.StageManager;

public class ProjectManagerHeader implements Initializable {
    @FXML
    private TextField SearchTF;
    @FXML
    private ImageView SearchBTN;
    @FXML
    private Button ImportBTN;
    @FXML
    private Button AddNewBTN;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SearchBTN.setOnMouseClicked(e -> Search(SearchTF.getText()));

        ImportBTN.setOnMouseClicked(e -> {
            CreateProject.setImportProject(true);
            Overlay.OpenOverlay("resources/components/CreateProject.fxml");
        });

        AddNewBTN.setOnMouseClicked(e -> {
            CreateProject.setImportProject(false);
            Overlay.OpenOverlay("resources/components/CreateProject.fxml");
        });

        Search("");
    }

    public static void Search(String text) {
        String Condition = "ID LIKE '%{Search}%' OR Name LIKE '%{Search}%' OR Path LIKE '%{Search}%' OR Version LIKE '%{Search}%' OR Repository LIKE '%{Search}%' OR Description LIKE '%{Search}%' OR Language LIKE '%{Search}%'";
        Condition = Condition.replace("{Search}", text);

        ArrayList<Project> Results = (ArrayList<Project>) database.Selector
                .Select(Tables.Projects, new String[] { Condition }, new OrderBy[] { OrderBy.LastEdited },
                        new Arrangement[] { Arrangement.DESC })
                .ToArrayList();

        StageManager.getListBox().getChildren().clear();
        if (Results.size() == 0)
            StageManager.getListBox().getChildren().add(StageManager.getNoResults());

        for (Project project : Results) {
            try {
                FXMLLoader loader = new FXMLLoader(new File("resources/components/EachProject.fxml").toURI().toURL());
                Parent parent = loader.load();
                StageManager.getListBox().getChildren().add(parent);
                ((EachProject) loader.getController()).setProject(project);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
