package controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import database.Tables;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.UsefulCode;
import stage.Overlay;
import stage.StageManager;

public class UsefulCodeHeader implements Initializable {
    @FXML
    private Button ViewAllBTN;
    @FXML
    private TextField SearchTF;
    @FXML
    private ImageView SearchBTN;
    @FXML
    private Button AddNewBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ViewAllBTN.setOnMouseClicked(e -> ShowAll());
        SearchBTN.setOnMouseClicked(e -> Search(SearchTF.getText()));

        AddNewBTN.setOnMouseClicked(e -> {
            Overlay.OpenOverlay("resources/components/CreateCode.fxml");
        });

        Search("");
    }

    public static void ShowAll() {
        Search("");
    }

    public static void Search(String text) {
        System.out.println("yay");

        String Condition = "ID LIKE '%{Search}%' OR Language LIKE '%{Search}%' OR Type LIKE '%{Search}%' OR Code LIKE '%{Search}%' OR Description LIKE '%{Search}%' OR Tags LIKE '%{Search}%' OR Input LIKE '%{Search}%' OR Output LIKE '%{Search}%'";
        Condition = Condition.replace("{Search}", text);

        ArrayList<UsefulCode> Results = (ArrayList<UsefulCode>) database.Selector
                .Select(Tables.UsefulCodes, new String[] { Condition }).ToArrayList();

        StageManager.getListBox().getChildren().clear();
        if (Results.size() == 0)
            StageManager.getListBox().getChildren().add(StageManager.getNoResults());

        for (UsefulCode usefulCode : Results) {
            try {
                FXMLLoader loader = new FXMLLoader(new File("resources/components/EachCode.fxml").toURI().toURL());
                Parent parent = loader.load();
                StageManager.getListBox().getChildren().addAll(parent);
                ((EachCode) loader.getController()).setUsefulCode(usefulCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
