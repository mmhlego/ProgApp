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
import model.Todo;
import stage.Overlay;
import stage.StageManager;

public class TodoListHeader implements Initializable {
    @FXML
    private TextField SearchTF;
    @FXML
    private ImageView SearchBTN;
    @FXML
    private Button AddNewBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AddNewBTN.setOnMouseClicked(e -> Overlay.OpenOverlay("resources/components/AddTodo.fxml"));

        SearchBTN.setOnMouseClicked(e -> Search(SearchTF.getText()));

        Search("");
    }

    public static void Search(String searchText) {
        String Condition = "ID LIKE '%{Search}%' OR Text LIKE '%{Search}%' OR Label LIKE '%{Search}%'";
        Condition = Condition.replace("{Search}", searchText);

        ArrayList<Todo> Results = (ArrayList<Todo>) database.Selector.Select(Tables.Todos, new String[] { Condition })
                .ToArrayList();

        StageManager.getListBox().getChildren().clear();
        if (Results.size() == 0)
            StageManager.getListBox().getChildren().add(StageManager.getNoResults());

        for (Todo todo : Results) {
            try {
                FXMLLoader loader = new FXMLLoader(new File("resources/components/EachTodo.fxml").toURI().toURL());
                Parent parent = loader.load();
                StageManager.getListBox().getChildren().add(parent);
                ((EachTodo) loader.getController()).setTodo(todo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
